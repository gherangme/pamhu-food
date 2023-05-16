package com.example.securityhibernate.security;

import com.example.securityhibernate.security.CustomOAuth2User;
import com.example.securityhibernate.security.CustomUserDetails;
import com.example.securityhibernate.security.CustomUserDetailsSerializer;
import com.example.securityhibernate.service.SignupService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomOAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private SignupService signupService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        System.out.println("AuthenticationSuccessHandler invoked");
        System.out.println("Authentication name: " + authentication.getName());
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        System.out.println(oauthUser.getAuthorities());
        System.out.println(oauthUser.getAttributes().toString());
        System.out.println(oauthUser.getEmail());

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(oauthUser.getEmail(), null, oauthUser.getAuthorities());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(CustomUserDetails.class, new CustomUserDetailsSerializer());
        Gson gson = gsonBuilder.create();
        String data = gson.toJson(token.getPrincipal());
        System.out.println(data);

        String email = oauthUser.getEmail();
        String name = oauthUser.getName();

        if (signupService.checkUser(email)) {
            signupService.signupByOAuth2Google(email, name);
        }

        Cookie cookie = new Cookie("email", email);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        response.sendRedirect("/home");

        super.onAuthenticationSuccess(request, response, authentication);
    }

}

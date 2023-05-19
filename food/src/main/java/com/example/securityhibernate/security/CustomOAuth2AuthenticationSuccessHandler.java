package com.example.securityhibernate.security;

import com.example.securityhibernate.listenum.ProviderColumn;
import com.example.securityhibernate.service.SignupService;
import com.example.securityhibernate.utils.CookieHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
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
//        System.out.println(oauthUser.getAttribute("login").toString());
        System.out.println(oauthUser.getEmail());
        String username = null;
        String name = null;
        // Google
        if (oauthUser.getEmail() != null) {
            username = oauthUser.getEmail();
            name = oauthUser.getName();
            if (signupService.checkUser(username)) {
                signupService.signupByOAuth2(username, name, ProviderColumn.GOOGLE);
            }
            // Create Cookie
            CookieHandle cookieHandle = new CookieHandle();
            cookieHandle.createCookie("username", username, response);

        // Github
        } else if (oauthUser.getAttribute("login").toString() != null) {
            username = oauthUser.getAttribute("login").toString();
            name = oauthUser.getName();
            if (signupService.checkUser(username)) {
                signupService.signupByOAuth2(username, name, ProviderColumn.GITHUB);
            }
            // Create Cookie
            CookieHandle cookieHandle = new CookieHandle();
            cookieHandle.createCookie("username", username, response);
        }

        response.sendRedirect("/home");

        super.onAuthenticationSuccess(request, response, authentication);
    }

}

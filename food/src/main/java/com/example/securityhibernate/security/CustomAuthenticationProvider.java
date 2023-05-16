package com.example.securityhibernate.security;

import com.example.securityhibernate.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    LoginService loginService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        try {
            String password = authentication.getCredentials().toString();
            if(loginService.checkLogin(username, password)){
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            } else {
                return null;
            }
        } catch (Exception e) {
            if(loginService.checkLoginByOAuth2Google(username)){
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            } else {
                return null;
            }
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

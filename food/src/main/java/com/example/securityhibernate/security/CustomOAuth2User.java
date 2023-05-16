package com.example.securityhibernate.security;

import com.example.securityhibernate.entity.Users;
import com.example.securityhibernate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private OAuth2User oauth2User;

    @Autowired
    private UserRepository userRepository;

    public CustomOAuth2User(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        try {
            Users users = userRepository.findByUsername(oauth2User.getAttributes().get("email").toString());
            System.out.println("check role "+oauth2User.getAttributes().get("email").toString());
            if (users.getRoles().getRoleName().equals("ROLE_ADMIN")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }

            if (users.getRoles().getRoleName().equals("ROLE_MANAGER")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
            }

            return authorities;
        } catch (Exception e) {
            return authorities;
        }
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    public String getEmail() {
        return oauth2User.<String>getAttribute("email");
    }
}

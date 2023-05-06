package com.example.securityhibernate.security;

import com.example.securityhibernate.entity.Users;
import com.example.securityhibernate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        Users users = userRepository.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(users);
    }


}
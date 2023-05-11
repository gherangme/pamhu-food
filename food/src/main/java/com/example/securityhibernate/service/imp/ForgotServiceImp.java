package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.entity.Users;
import com.example.securityhibernate.repository.UserRepository;
import com.example.securityhibernate.service.ForgotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ForgotServiceImp implements ForgotService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean checkUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public boolean changPassword(String username, String password) {
        try {
            Users users = userRepository.findByUsername(username);
            users.setPassword(passwordEncoder.encode(password));
            userRepository.save(users);
            return true;
        } catch (Exception e) {
            System.out.println("Error changPassword "+e.getMessage());
            return false;
        }
    }
}

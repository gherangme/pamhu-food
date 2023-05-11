package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.entity.Roles;
import com.example.securityhibernate.entity.Users;
import com.example.securityhibernate.repository.UserRepository;
import com.example.securityhibernate.service.LoginService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @Override
    public boolean checkLogin(String username, String password) {
        return passwordEncoder.matches(password, userRepository.findByUsername(username).getPassword());
    }

    @Override
    public int getIdUserByUsername(String username) {
        Users users = userRepository.findByUsername(username);
        int id = users.getId();
        return id;
    }

    @Override
    public String getFullNameByToken(String token) {
        try {
            return userRepository.findByUsername(jwtUtilsHelpers.getUsernameByToken(token)).getFullname();
        } catch (Exception e) {
            return "Guess";
        }
    }

}

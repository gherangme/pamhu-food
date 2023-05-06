package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.entity.Roles;
import com.example.securityhibernate.entity.Users;
import com.example.securityhibernate.repository.UserRepository;
import com.example.securityhibernate.service.LoginService;
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

    @Override
    public boolean addUser(String username, String password, String role) {

        try {
            Users users = new Users();
            users.setUsername(username);
            users.setPassword(passwordEncoder.encode(password));
            users.setRole(role);

            Roles roles = new Roles();
            roles.setId(3);
            users.setRoles(roles);

            userRepository.save(users);

            return true;
        } catch (Exception e) {
            System.out.println("Error addUser service" + e.getMessage());
            return false;
        }
    }

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

}

package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.SignupDTO;
import com.example.securityhibernate.dto.UserDTO;
import com.example.securityhibernate.entity.Roles;
import com.example.securityhibernate.entity.Users;
import com.example.securityhibernate.repository.UserRepository;
import com.example.securityhibernate.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupServiceImp implements SignupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean singup(SignupDTO signupDTO) {
        try {
            Users users = new Users();
            users.setFullname(signupDTO.getFullName());
            users.setUsername(signupDTO.getUsername());
            users.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
            users.setAddress(signupDTO.getAddress());
            users.setPhone(signupDTO.getPhone());
//            users.setRole("ROLE_USER");

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
    public boolean checkUser(String username) {
        return userRepository.findByUsername(username) == null;
    }
}

package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.UserDTO;
import com.example.securityhibernate.entity.Users;
import com.example.securityhibernate.repository.UserRepository;
import com.example.securityhibernate.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImp implements CheckoutService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getUserByUsername(String username) {
        Users users = userRepository.findByUsername(username);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(users.getId());
        userDTO.setUsername(users.getUsername());
        userDTO.setFullName(users.getFullname());
        userDTO.setAddress(users.getAddress());
        userDTO.setPhone(users.getPhone());

        return userDTO;
    }
}

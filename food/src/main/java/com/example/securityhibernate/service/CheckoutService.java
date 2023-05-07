package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.UserDTO;

public interface CheckoutService {

    UserDTO getUserByUsername(String username);

}

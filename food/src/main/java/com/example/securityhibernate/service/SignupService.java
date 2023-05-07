package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.SignupDTO;
import com.example.securityhibernate.dto.UserDTO;

public interface SignupService {

    boolean singup(SignupDTO signupDTO);
    boolean checkUser(String username);

}

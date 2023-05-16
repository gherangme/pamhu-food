package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.SignupDTO;
import com.example.securityhibernate.dto.UserDTO;
import com.example.securityhibernate.listenum.Provider;

public interface SignupService {

    boolean singup(SignupDTO signupDTO);
    boolean checkUser(String username);
    boolean signupByOAuth2(String username, String name, Provider provider);

}

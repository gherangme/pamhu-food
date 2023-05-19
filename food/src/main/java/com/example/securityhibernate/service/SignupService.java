package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.SignupDTO;
import com.example.securityhibernate.listenum.ProviderColumn;

public interface SignupService {

    boolean singup(SignupDTO signupDTO);
    boolean checkUser(String username);
    boolean signupByOAuth2(String username, String name, ProviderColumn providerColumn);

}

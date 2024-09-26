package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.request.SignupDTO;
import com.example.securityhibernate.enums.ProviderColumn;

public interface SignupService {

    boolean singup(SignupDTO signupDTO);
    boolean checkUser(String username);
    boolean signupByOAuth2(String username, String name, ProviderColumn providerColumn);

}

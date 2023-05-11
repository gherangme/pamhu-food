package com.example.securityhibernate.service;

public interface ForgotService {

    boolean checkUsername(String username);
    boolean changePassword(String username, String password);

}

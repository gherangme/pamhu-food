package com.example.securityhibernate.service;

public interface ForgotService {

    boolean checkUsername(String username);
    boolean changPassword(String username, String password);

}

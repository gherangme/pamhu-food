package com.example.securityhibernate.service;

public interface LoginService {

    boolean checkLogin(String username, String password);
    int getIdUserByUsername(String username);
    String getFullNameByToken(String tokeb);

}

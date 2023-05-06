package com.example.securityhibernate.service;

public interface LoginService {

    boolean addUser(String username, String password, String role);
    boolean checkLogin(String username, String password);
    int getIdUserByUsername(String username);

}

package com.example.securityhibernate.service;

public interface OrderService {
    boolean saveOrder(String username, int idFood, int amount, double price);
}

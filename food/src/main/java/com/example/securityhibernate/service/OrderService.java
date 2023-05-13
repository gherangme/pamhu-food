package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.OrderDetailDTO;

import java.util.List;

public interface OrderService {

    boolean saveOrder(String username, int idFood, int amount, double price);

}

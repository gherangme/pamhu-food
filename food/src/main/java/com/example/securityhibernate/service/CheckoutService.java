package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.CheckoutDTO;
import com.example.securityhibernate.dto.FoodDTO;
import com.example.securityhibernate.dto.UserDTO;

import java.util.List;

public interface CheckoutService {

    UserDTO getUserByUsername(String username);
    int checkout(CheckoutDTO checkoutDTO);
    List<FoodDTO> getListFoodCheckout(String username);

}

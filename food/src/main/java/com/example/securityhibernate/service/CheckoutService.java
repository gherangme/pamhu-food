package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.response.CheckoutDTO;
import com.example.securityhibernate.dto.request.FoodDTO;
import com.example.securityhibernate.dto.request.UserDTO;

import java.util.List;

public interface CheckoutService {

    UserDTO getUserByUsername(String username);
    int checkout(CheckoutDTO checkoutDTO);
    List<FoodDTO> getListFoodCheckout(String username);

}

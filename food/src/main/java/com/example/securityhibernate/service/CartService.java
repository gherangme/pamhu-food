package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.FoodDTO;

import java.util.List;

public interface CartService {

    List<FoodDTO> getListFoods(int idFood,  String username);
    boolean deleteItemOder(int idFood, String username);

}

package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.FoodDTO;

import java.util.List;

public interface FoodService {

    List<FoodDTO> getAllFoodsByIdCategory(int idCategory);
    List<FoodDTO> getAllFoodsPageHome();
    List<FoodDTO> getAllFoods(int pageNumber);

}

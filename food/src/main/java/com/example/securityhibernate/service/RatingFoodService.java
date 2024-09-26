package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.response.RatingFoodDTO;

import java.util.List;

public interface RatingFoodService {

    List<RatingFoodDTO> getAllRatingFoodByIdFood(int id);
    boolean addRatingFood(String username, int idFood, int star, String comment);

}

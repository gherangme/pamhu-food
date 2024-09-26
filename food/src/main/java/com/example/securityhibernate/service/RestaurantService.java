package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.request.RestaurantDTO;

import java.util.List;

public interface RestaurantService {

    List<RestaurantDTO> getAllRestaurants();
    List<RestaurantDTO> getAllInformation();

}

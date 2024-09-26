package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.request.RestaurantDetailDTO;

public interface RestaurantDetailService {

    RestaurantDetailDTO getRestaurantDetailById(int id);

}

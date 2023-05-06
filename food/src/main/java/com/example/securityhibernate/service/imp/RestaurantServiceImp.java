package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.CategaryRestaurantDTO;
import com.example.securityhibernate.dto.CategoryDTO;
import com.example.securityhibernate.dto.RestaurantDTO;
import com.example.securityhibernate.entity.CategoryRestaurant;
import com.example.securityhibernate.entity.Restaurant;
import com.example.securityhibernate.entity.Users;
import com.example.securityhibernate.repository.CategoryRepository;
import com.example.securityhibernate.repository.CategoryRestaurantRepository;
import com.example.securityhibernate.repository.RestaurantRepository;
import com.example.securityhibernate.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRestaurantRepository categoryRestaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<RestaurantDTO> getAllRestaurant() {
        List<Restaurant> list = restaurantRepository.findAll();
        List<RestaurantDTO> dtoList = new ArrayList<>();
        for (Restaurant restaurant: list) {
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setId(restaurant.getId());
            restaurantDTO.setName(restaurant.getName());
            restaurantDTO.setImage(restaurant.getImage());
            restaurantDTO.setAddress(restaurant.getAddress());
            restaurantDTO.setRating(restaurant.getRating());

            List<CategoryRestaurant> list1 = categoryRestaurantRepository
                    .findAllByRestaurant_Id(restaurant.getId());
            if (list1.size()<2) {
                restaurantDTO.setCate(categoryRepository
                        .findById(list1.get(0).getCategory().getId()).getName());
            }

            if (restaurant.isFreeShip()) {
                restaurantDTO.setFreeShip("Free ship");
            } else {
                restaurantDTO.setFreeShip("Not free ship");
            }

            dtoList.add(restaurantDTO);
        }
        return dtoList;
    }
}

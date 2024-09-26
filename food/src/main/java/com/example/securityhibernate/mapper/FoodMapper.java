package com.example.securityhibernate.mapper;

import com.example.securityhibernate.dto.response.CategaryRestaurantDTO;
import com.example.securityhibernate.dto.request.FoodDTO;
import com.example.securityhibernate.entity.Food;
import com.example.securityhibernate.repository.CategoryRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

    @Autowired
    private CategoryRestaurantRepository categoryRestaurantRepository;

    public FoodDTO convertEntityToDTO(Food food) {
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setId(food.getId());
        foodDTO.setName(food.getName());
        foodDTO.setImage(food.getImage());
        foodDTO.setPrice(food.getPrice());
        CategaryRestaurantDTO categaryRestaurantDTO = new CategaryRestaurantDTO();
        categaryRestaurantDTO.setId(food.getCategoryRestaurant().getId());
        categaryRestaurantDTO.setIdRes(food.getCategoryRestaurant().getRestaurant().getId());
        categaryRestaurantDTO.setIdCate(food.getCategoryRestaurant().getCategory().getId());
        foodDTO.setCategaryRestaurantDTO(categaryRestaurantDTO);

        return foodDTO;
    }

    public Food convertDTOToEntity(FoodDTO foodDTO) {
        Food food = new Food();
        food.setId(foodDTO.getId());
        food.setName(foodDTO.getName());
        food.setImage(foodDTO.getImage());
        food.setPrice(foodDTO.getPrice());
        food.setCategoryRestaurant(categoryRestaurantRepository.findById(foodDTO.getCategaryRestaurantDTO().getId()));

        return food;
    }

}

package com.example.securityhibernate.mapper;

import com.example.securityhibernate.dto.CategaryRestaurantDTO;
import com.example.securityhibernate.entity.CategoryRestaurant;
import com.example.securityhibernate.repository.CategoryRepository;
import com.example.securityhibernate.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryRestaurantMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public CategaryRestaurantDTO convertEntityToDTO(CategoryRestaurant categoryRestaurant) {
        CategaryRestaurantDTO categaryRestaurantDTO = new CategaryRestaurantDTO();
        categaryRestaurantDTO.setId(categoryRestaurant.getId());
        categaryRestaurantDTO.setIdCate(categoryRestaurant.getCategory().getId());
        categaryRestaurantDTO.setIdRes(categoryRestaurant.getRestaurant().getId());
        return categaryRestaurantDTO;
    }

    public CategoryRestaurant convertDTOToEntity(CategaryRestaurantDTO categaryRestaurantDTO) {
        CategoryRestaurant categoryRestaurant = new CategoryRestaurant();
        categoryRestaurant.setId(categaryRestaurantDTO.getId());
        categoryRestaurant.setCategory(categoryRepository.findById(categaryRestaurantDTO.getIdCate()));
        categoryRestaurant.setRestaurant(restaurantRepository.findById(categaryRestaurantDTO.getId()));
        return categoryRestaurant;
    }

}

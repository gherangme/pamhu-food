package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.request.CategoryDTO;
import com.example.securityhibernate.dto.request.FoodDTO;
import com.example.securityhibernate.entity.Food;
import com.example.securityhibernate.entity.RatingFood;
import com.example.securityhibernate.repository.FoodRepository;
import com.example.securityhibernate.repository.RatingFoodRepository;
import com.example.securityhibernate.service.FoodDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FoodDetailServiceImp implements FoodDetailService {

    FoodRepository foodRepository;
    RatingFoodRepository ratingFoodRepository;

    @Override
    public FoodDTO getFoodById(int id) {

        Food food = foodRepository.findById(id);
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setId(food.getId());
        foodDTO.setPrice(food.getPrice());
        foodDTO.setImage(food.getImage());
        foodDTO.setName(food.getName());

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(food.getCategoryRestaurant().getCategory().getId());
        categoryDTO.setName(food.getCategoryRestaurant().getCategory().getName());
        foodDTO.setCategoryDTO(categoryDTO);

        List<RatingFood> ratingFoodList = ratingFoodRepository.findAllByFood_Id(food.getId());
        if (ratingFoodList.size() > 0) {
            foodDTO.setRatingNumber(ratingFoodList.size());
            float star = 0;
            for (RatingFood ratingFood : ratingFoodList) {
                star += ratingFood.getStar();
            }
            foodDTO.setStar(star / foodDTO.getRatingNumber());
        }

        return foodDTO;
    }
}

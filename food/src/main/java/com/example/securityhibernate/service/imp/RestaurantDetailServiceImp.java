package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.request.CategoryDTO;
import com.example.securityhibernate.dto.request.FoodDTO;
import com.example.securityhibernate.dto.request.RestaurantDetailDTO;
import com.example.securityhibernate.entity.*;
import com.example.securityhibernate.mapper.CategoryMapper;
import com.example.securityhibernate.mapper.CouponMapper;
import com.example.securityhibernate.repository.*;
import com.example.securityhibernate.service.RestaurantDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestaurantDetailServiceImp implements RestaurantDetailService {

    CategoryMapper categoryMapper;
    CouponMapper couponMapper;
    RestaurantRepository restaurantRepository;
    CategoryRestaurantRepository categoryRestaurantRepository;
    CategoryRepository categoryRepository;
    FoodRepository foodRepository;
    RatingFoodRepository ratingFoodRepository;
    CouponRepository couponRepository;
    RatingRestaurantRepository ratingRestaurantRepository;

    @Override
    public RestaurantDetailDTO getRestaurantDetailById(int id) {
        RestaurantDetailDTO restaurantDetailDTO = new RestaurantDetailDTO();
        Restaurant restaurant = restaurantRepository.findById(id);

        restaurantDetailDTO.setId(restaurant.getId());
        restaurantDetailDTO.setName(restaurant.getName());
        restaurantDetailDTO.setImage(restaurant.getImage());
        restaurantDetailDTO.setAddress(restaurant.getAddress());

        Coupon coupon = couponRepository.findById(restaurant.getCoupon().getId());
        restaurantDetailDTO.setCouponDTO(couponMapper.convertEntityToDTO(coupon));

        List<RatingRestaurant> ratingRestaurant = ratingRestaurantRepository.findByRestaurant_Id(restaurant.getId());
        float starRes = 0;
        for (RatingRestaurant ratingRestaurant1: ratingRestaurant) {
            starRes += ratingRestaurant1.getStar();
        }
        restaurantDetailDTO.setRating(starRes / ratingRestaurant.size());

        restaurantDetailDTO.setDesc(restaurant.getDesc());

        List<CategoryRestaurant> list1 = categoryRestaurantRepository
                .findAllByRestaurant_Id(restaurant.getId());
        if (list1.size()<2) {
            restaurantDetailDTO.setCate(categoryRepository
                    .findById(list1.get(0).getCategory().getId()).getName());
        }

        List<FoodDTO> dtoList = new ArrayList<>();
        for (CategoryRestaurant categoryRestaurant: list1) {
            List<Food> foodList = foodRepository.findByCategoryRestaurant_Id(categoryRestaurant.getId());
            for (Food food: foodList) {
                if (food != null) {
                    FoodDTO foodDTO = new FoodDTO();
                    foodDTO.setId(food.getId());
                    foodDTO.setName(food.getName());
                    foodDTO.setPrice(food.getPrice());
                    foodDTO.setImage(food.getImage());

                    foodDTO.setCategoryDTO(categoryMapper.convertEntityToDTO(food.getCategoryRestaurant().getCategory()));

                    List<RatingFood> ratingFoodList = ratingFoodRepository.findAllByFood_Id(food.getId());
                    if (ratingFoodList.size() > 0) {
                        foodDTO.setRatingNumber(ratingFoodList.size());
                        float star = 0;
                        for (RatingFood ratingFood : ratingFoodList) {
                            star += ratingFood.getStar();
                        }
                        foodDTO.setStar(star / foodDTO.getRatingNumber());
                    }

                    dtoList.add(foodDTO);
                }
                restaurantDetailDTO.setFoodDTO(dtoList);
            }
        }

        restaurantDetailDTO.setDishes(dtoList.size());

        List<CategoryDTO> list = new ArrayList<>();
        for (CategoryRestaurant categoryRestaurant: list1) {
            Category category = categoryRepository.findById(categoryRestaurant.getCategory().getId());
            CategoryDTO categoryDTO = categoryMapper.convertEntityToDTO(category);

            boolean exists = false;

            for(CategoryDTO existingCategory : list) {
                if(existingCategory.getName().equals(categoryDTO.getName())) {
                    exists = true;
                    break;
                }
            }

            if(!exists) {
                list.add(categoryDTO);
            }
        }
        restaurantDetailDTO.setCategoryDTO(list);

        return restaurantDetailDTO;
    }
}

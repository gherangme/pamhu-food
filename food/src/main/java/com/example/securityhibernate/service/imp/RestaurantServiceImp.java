package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.CategaryRestaurantDTO;
import com.example.securityhibernate.dto.CategoryDTO;
import com.example.securityhibernate.dto.CouponDTO;
import com.example.securityhibernate.dto.RestaurantDTO;
import com.example.securityhibernate.entity.*;
import com.example.securityhibernate.mapper.CouponMapper;
import com.example.securityhibernate.mapper.RestaurantMapper;
import com.example.securityhibernate.repository.*;
import com.example.securityhibernate.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImp implements RestaurantService {

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRestaurantRepository categoryRestaurantRepository;

    @Autowired
    private RatingRestaurantRepository ratingRestaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public List<RestaurantDTO> getAllRestaurant() {
        List<Restaurant> list = restaurantRepository.findAll();
        List<RestaurantDTO> dtoList = getAllRestaurantsCommon(list);
        return dtoList;
    }

    @Override
    public List<RestaurantDTO> getAllPageHome() {
        List<Restaurant> list = restaurantRepository.findAllPageHome();
        List<RestaurantDTO> dtoList = getAllRestaurantsCommon(list);
        return dtoList;
    }

    // Common All Restaurants
    private List<RestaurantDTO> getAllRestaurantsCommon(List<Restaurant> list) {
        List<RestaurantDTO> dtoList = new ArrayList<>();

        for (Restaurant restaurant: list) {
            RestaurantDTO restaurantDTO = restaurantMapper.convertEntityToDTO(restaurant);

            // Set rating restaurant
            List<RatingRestaurant> ratingRestaurant = ratingRestaurantRepository.findByRestaurant_Id(restaurant.getId());
            float star = 0;
            for (RatingRestaurant ratingRestaurant1: ratingRestaurant) {
                star += ratingRestaurant1.getStar();
            }
            restaurantDTO.setRating(star / ratingRestaurant.size());

            // Set cate res
            List<CategoryRestaurant> list1 = categoryRestaurantRepository
                    .findAllByRestaurant_Id(restaurant.getId());
            if (list1.size()<2) {
                restaurantDTO.setCate(categoryRepository
                        .findById(list1.get(0).getCategory().getId()).getName());
            }

            // Set infor Coupon
            Coupon coupon = couponRepository.findById(restaurant.getCoupon().getId());
            restaurantDTO.setCouponDTO(couponMapper.convertEntityToDTO(coupon));

            dtoList.add(restaurantDTO);
        }
        return dtoList;
    }
}

package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.request.RestaurantDTO;
import com.example.securityhibernate.entity.*;
import com.example.securityhibernate.mapper.CouponMapper;
import com.example.securityhibernate.mapper.RestaurantMapper;
import com.example.securityhibernate.repository.*;
import com.example.securityhibernate.service.RestaurantService;
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
public class RestaurantServiceImp implements RestaurantService {

    RestaurantMapper restaurantMapper;
    CouponMapper couponMapper;
    RestaurantRepository restaurantRepository;
    CategoryRestaurantRepository categoryRestaurantRepository;
    RatingRestaurantRepository ratingRestaurantRepository;
    CategoryRepository categoryRepository;
    CouponRepository couponRepository;

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> list = restaurantRepository.findAll();
        List<RestaurantDTO> dtoList = utils(list);
        return dtoList;
    }

    @Override
    public List<RestaurantDTO> getAllInformation() {
        List<Restaurant> list = restaurantRepository.findAllPageHome();
        List<RestaurantDTO> dtoList = utils(list);
        return dtoList;
    }

    private List<RestaurantDTO> utils(List<Restaurant> list) {
        List<RestaurantDTO> dtoList = new ArrayList<>();

        for (Restaurant restaurant: list) {
            RestaurantDTO restaurantDTO = restaurantMapper.convertEntityToDTO(restaurant);

            List<RatingRestaurant> ratingRestaurant = ratingRestaurantRepository.findByRestaurant_Id(restaurant.getId());
            float star = 0;
            for (RatingRestaurant ratingRestaurant1: ratingRestaurant) {
                star += ratingRestaurant1.getStar();
            }
            restaurantDTO.setRating(star / ratingRestaurant.size());

            List<CategoryRestaurant> list1 = categoryRestaurantRepository
                    .findAllByRestaurant_Id(restaurant.getId());
            if (list1.size()<2) {
                restaurantDTO.setCate(categoryRepository
                        .findById(list1.get(0).getCategory().getId()).getName());
            }

            Coupon coupon = couponRepository.findById(restaurant.getCoupon().getId());
            restaurantDTO.setCouponDTO(couponMapper.convertEntityToDTO(coupon));

            dtoList.add(restaurantDTO);
        }
        return dtoList;
    }
}

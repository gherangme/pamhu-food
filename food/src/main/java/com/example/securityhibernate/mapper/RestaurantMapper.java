package com.example.securityhibernate.mapper;

import com.example.securityhibernate.dto.request.RestaurantDTO;
import com.example.securityhibernate.dto.request.UserDTO;
import com.example.securityhibernate.entity.Coupon;
import com.example.securityhibernate.entity.Restaurant;
import com.example.securityhibernate.repository.CouponRepository;
import com.example.securityhibernate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserRepository userRepository;

    public RestaurantDTO convertEntityToDTO(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setImage(restaurant.getImage());
        restaurantDTO.setAddress(restaurant.getAddress());
        restaurantDTO.setDescription(restaurant.getDesc());

        Coupon coupon = restaurant.getCoupon();
        restaurantDTO.setCouponDTO(couponMapper.convertEntityToDTO(coupon));

        UserDTO userDTO = new UserDTO();
        userDTO.setId(restaurant.getUsers().getId());
        userDTO.setUsername(restaurant.getUsers().getUsername());
        userDTO.setFullName(restaurant.getUsers().getFullname());
        userDTO.setAddress(restaurant.getUsers().getAddress());
        userDTO.setPhone(restaurant.getUsers().getPhone());

        return restaurantDTO;
    }

    public Restaurant convertDTOToEntity(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantDTO.getId());
        restaurant.setName(restaurantDTO.getName());
        restaurant.setImage(restaurantDTO.getImage());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setDesc(restaurantDTO.getDescription());
        restaurant.setCoupon(couponRepository.findById(restaurantDTO.getCouponDTO().getId()));
        restaurant.setUsers(userRepository.findByUsername(restaurantDTO.getUserDTO().getUsername()));
        return restaurant;
    }

}

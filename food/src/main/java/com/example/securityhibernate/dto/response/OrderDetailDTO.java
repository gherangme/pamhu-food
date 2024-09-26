package com.example.securityhibernate.dto.response;

import com.example.securityhibernate.dto.request.CouponDTO;
import com.example.securityhibernate.dto.request.FoodDTO;
import com.example.securityhibernate.dto.request.RestaurantDTO;

import java.util.List;

public class OrderDetailDTO {

    private int id;
    private RestaurantDTO restaurantDTO;
    private double totalPrice;
    private String date;
    private List<FoodDTO> foodDTOList;
    private CouponDTO couponDTO;

    public RestaurantDTO getRestaurantDTO() {
        return restaurantDTO;
    }

    public void setRestaurantDTO(RestaurantDTO restaurantDTO) {
        this.restaurantDTO = restaurantDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<FoodDTO> getFoodDTOList() {
        return foodDTOList;
    }

    public void setFoodDTOList(List<FoodDTO> foodDTOList) {
        this.foodDTOList = foodDTOList;
    }

    public CouponDTO getCouponDTO() {
        return couponDTO;
    }

    public void setCouponDTO(CouponDTO couponDTO) {
        this.couponDTO = couponDTO;
    }
}

package com.example.securityhibernate.dto.response;

import com.example.securityhibernate.dto.request.CategoryDTO;
import com.example.securityhibernate.dto.request.CouponDTO;
import com.example.securityhibernate.dto.request.FoodDTO;
import com.example.securityhibernate.dto.request.RestaurantDetailDTO;

import java.util.List;

public class ManagerDTO {

    private RestaurantDetailDTO restaurantDetailDTO;
    private double totalIncome;
    private int totalOrders;
    private int totalCategorys;
    private int totalFoods;
    private List<FoodDTO> foodDTOList;
    private List<CategoryDTO> categoryDTOList;
    private CouponDTO couponDTO;

    public RestaurantDetailDTO getRestaurantDetailDTO() {
        return restaurantDetailDTO;
    }

    public void setRestaurantDetailDTO(RestaurantDetailDTO restaurantDetailDTO) {
        this.restaurantDetailDTO = restaurantDetailDTO;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getTotalCategorys() {
        return totalCategorys;
    }

    public void setTotalCategorys(int totalCategorys) {
        this.totalCategorys = totalCategorys;
    }

    public int getTotalFoods() {
        return totalFoods;
    }

    public void setTotalFoods(int totalFoods) {
        this.totalFoods = totalFoods;
    }

    public List<FoodDTO> getFoodDTOList() {
        return foodDTOList;
    }

    public void setFoodDTOList(List<FoodDTO> foodDTOList) {
        this.foodDTOList = foodDTOList;
    }

    public List<CategoryDTO> getCategoryDTOList() {
        return categoryDTOList;
    }

    public void setCategoryDTOList(List<CategoryDTO> categoryDTOList) {
        this.categoryDTOList = categoryDTOList;
    }

    public CouponDTO getCouponDTO() {
        return couponDTO;
    }

    public void setCouponDTO(CouponDTO couponDTO) {
        this.couponDTO = couponDTO;
    }
}

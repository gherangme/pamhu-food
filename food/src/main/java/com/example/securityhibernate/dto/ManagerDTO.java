package com.example.securityhibernate.dto;

import java.util.List;

public class ManagerDTO {

    private double totalIncome;
    private int totalOrders;
    private int totalCategorys;
    private int totalFoods;
    private List<FoodDTO> foodDTOList;
    private List<CategoryDTO> categoryDTOList;
    private List<PromotionDTO> promotionDTOList;

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

    public List<PromotionDTO> getPromotionDTOList() {
        return promotionDTOList;
    }

    public void setPromotionDTOList(List<PromotionDTO> promotionDTOList) {
        this.promotionDTOList = promotionDTOList;
    }
}

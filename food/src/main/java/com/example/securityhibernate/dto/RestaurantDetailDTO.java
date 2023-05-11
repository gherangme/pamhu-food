package com.example.securityhibernate.dto;

import java.util.List;

public class RestaurantDetailDTO {

    private int id;
    private String name;
    private float rating;
    private String address;
    private String image;
    private String desc;
    private String cate = "Nhiều loại";
    private List<CategoryDTO> categoryDTO;
    private CouponDTO couponDTO;
    private int dishes = 0;
    private List<FoodDTO> foodDTO;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<CategoryDTO> getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(List<CategoryDTO> categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public CouponDTO getCouponDTO() {
        return couponDTO;
    }

    public void setCouponDTO(CouponDTO couponDTO) {
        this.couponDTO = couponDTO;
    }

    public int getDishes() {
        return dishes;
    }

    public void setDishes(int dishes) {
        this.dishes = dishes;
    }

    public List<FoodDTO> getFoodDTO() {
        return foodDTO;
    }

    public void setFoodDTO(List<FoodDTO> foodDTO) {
        this.foodDTO = foodDTO;
    }
}

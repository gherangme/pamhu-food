package com.example.securityhibernate.dto;

public class FoodDTO {

    private int id;
    private String name;
    private double price;
    private String image;
    private int amount;
    private CategaryRestaurantDTO categaryRestaurantDTO;
    private CategoryDTO categoryDTO;
    private RestaurantDTO restaurantDTO;
    private int ratingNumber = 0;
    private float star = 5;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public RestaurantDTO getRestaurantDTO() {
        return restaurantDTO;
    }

    public void setRestaurantDTO(RestaurantDTO restaurantDTO) {
        this.restaurantDTO = restaurantDTO;
    }

    public int getRatingNumber() {
        return ratingNumber;
    }

    public void setRatingNumber(int ratingNumber) {
        this.ratingNumber = ratingNumber;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CategaryRestaurantDTO getCategaryRestaurantDTO() {
        return categaryRestaurantDTO;
    }

    public void setCategaryRestaurantDTO(CategaryRestaurantDTO categaryRestaurantDTO) {
        this.categaryRestaurantDTO = categaryRestaurantDTO;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }
}

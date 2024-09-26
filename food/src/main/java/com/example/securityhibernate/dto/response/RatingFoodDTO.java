package com.example.securityhibernate.dto.response;

import com.example.securityhibernate.dto.request.FoodDTO;
import com.example.securityhibernate.dto.request.UserDTO;

public class RatingFoodDTO {

    private int id;
    private String comment;
    private int star;
    private UserDTO userDTO;
    private FoodDTO foodDTO;
    private String date;

    public FoodDTO getFoodDTO() {
        return foodDTO;
    }

    public void setFoodDTO(FoodDTO foodDTO) {
        this.foodDTO = foodDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

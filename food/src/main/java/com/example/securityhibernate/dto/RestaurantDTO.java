package com.example.securityhibernate.dto;

public class RestaurantDTO {

    private int id;
    private String name;
    private float rating;
    private String address;
    private String image;
    private String cate = "Nhiều loại";
    private String freeShip;

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getFreeShip() {
        return freeShip;
    }

    public void setFreeShip(String freeShip) {
        this.freeShip = freeShip;
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
}

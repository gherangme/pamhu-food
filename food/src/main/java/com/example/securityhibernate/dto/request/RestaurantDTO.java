package com.example.securityhibernate.dto.request;

public class RestaurantDTO {

    private int id;
    private String name;
    private float rating;
    private String address;
    private String image;
    private String cate = "Nhiều loại";
    private String description;
    private CouponDTO couponDTO;
    private UserDTO userDTO;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

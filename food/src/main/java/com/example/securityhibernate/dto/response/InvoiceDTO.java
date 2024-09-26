package com.example.securityhibernate.dto.response;

import com.example.securityhibernate.dto.request.FoodDTO;

import java.util.List;

public class InvoiceDTO {

    private int id;
    private String name;
    private String phone;
    private String address;
    private double totalPrice;
    private String date;
    private List<FoodDTO> foodDTOList;
    private float voucher;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public float getVoucher() {
        return voucher;
    }

    public void setVoucher(float voucher) {
        this.voucher = voucher;
    }
}

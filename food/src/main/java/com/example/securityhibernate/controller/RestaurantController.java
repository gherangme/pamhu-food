package com.example.securityhibernate.controller;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        ResponseData responseData = new ResponseData();
        responseData.setData(restaurantService.getAllRestaurant());
        responseData.setDesc("Lấy thành công danh sách nhà hàng");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}

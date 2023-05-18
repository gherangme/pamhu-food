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

    // Get All Restaurant Page Home
    @GetMapping("/getAllPageHome")
    public ResponseEntity<?> getAllPageHome() {
        return new ResponseEntity<>(new ResponseData(restaurantService.getAllPageHome(),
                "Lấy thành công danh sách nhà hàng Page Home"), HttpStatus.OK);
    }

    // Get All Restaurant
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(new ResponseData(restaurantService.getAllRestaurant(),
                "Lấy thành công danh sách nhà hàng"), HttpStatus.OK);
    }

}

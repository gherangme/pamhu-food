package com.example.securityhibernate.controller;

import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.RestaurantService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    // Get All Restaurant Page Home
    @GetMapping("/getAllPageHome")
    public ResponseEntity<?> getAllPageHome() {
        return new ResponseEntity<>(new ResponseData(restaurantService.getAllInformation(),
                "Lấy thành công danh sách nhà hàng Page Home"), HttpStatus.OK);
    }

    // Get All Restaurant
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(new ResponseData(restaurantService.getAllRestaurants(),
                "Lấy thành công danh sách nhà hàng"), HttpStatus.OK);
    }

}

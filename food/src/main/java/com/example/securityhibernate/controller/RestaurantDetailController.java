package com.example.securityhibernate.controller;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.RestaurantDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/restaurant-detail")
public class RestaurantDetailController {

    @Autowired
    private RestaurantDetailService restaurantDetailService;

    private int idRes = 0;

    @GetMapping("/getIdRestaurant/{id}")
    public ResponseEntity<?> postIdRestaurant(@PathVariable int id) {
        idRes = id;
        ResponseData responseData = new ResponseData();
        responseData.setData(idRes);
        responseData.setDesc("Post thành công id restaurant");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/getRestaurantById")
    public ResponseEntity<?> getRestaurantById() {
        ResponseData responseData = new ResponseData();
        responseData.setData(restaurantDetailService.getRestaurantDetailById(idRes));
        responseData.setDesc("Lấy thành công res detail");

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}

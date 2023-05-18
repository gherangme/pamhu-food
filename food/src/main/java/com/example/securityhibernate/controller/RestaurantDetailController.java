package com.example.securityhibernate.controller;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.RestaurantDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/restaurant-detail")
public class RestaurantDetailController {

    private int idRes = 0;

    @Autowired
    private RestaurantDetailService restaurantDetailService;

    // Get Id Restaurant Detail
    @GetMapping("/getIdRestaurant/{id}")
    public ResponseEntity<?> postIdRestaurant(@PathVariable int id) {
        idRes = id;

        return new ResponseEntity<>(new ResponseData(idRes,
                "Lấy thành công id restaurant"), HttpStatus.OK);
    }

    // Get Infor Restaurant Detail By Id
    @GetMapping("/getRestaurantById")
    public ResponseEntity<?> getRestaurantById() {
        List<ResponseData> list = new ArrayList<>();
        list.add(new ResponseData(restaurantDetailService.getRestaurantDetailById(idRes),
                "Lấy thành công res detail"));

        list.add(new ResponseData(idRes,
                "Lấy thành công id res"));

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}

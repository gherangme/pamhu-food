package com.example.securityhibernate.controller;

import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.RestaurantDetailService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/restaurant-details")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantDetailController {

    int idRes = 0;

    @Autowired
    RestaurantDetailService restaurantDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getIdRestaurant(@PathVariable int id) {
        idRes = id;

        return new ResponseEntity<>(new ResponseData(idRes,
                "Lấy thành công id restaurant"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getRestaurantDetail() {
        List<ResponseData> list = new ArrayList<>();
        list.add(new ResponseData(restaurantDetailService.getRestaurantDetailById(idRes)));
        list.add(new ResponseData(idRes));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}

package com.example.securityhibernate.controller;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        ResponseData responseData = new ResponseData();
        responseData.setData(foodService.getAllFoods());
        responseData.setDesc("Lấy thành công danh sách food");

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}

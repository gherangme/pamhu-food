package com.example.securityhibernate.controller;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/food")
public class FoodController {

    int idCategory = 0;

    @Autowired
    private FoodService foodService;

    @PostMapping("/postIdCategory")
    public ResponseEntity<?> postIdCategory(@RequestParam int id) {
        ResponseData responseData = new ResponseData();
        if (id != 0) {
            idCategory = id;
            responseData.setDesc("Lấy thành công id");
        } else {
            responseData.setDesc("Lấy thất bại id");
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/getAllPageHome")
    public ResponseEntity<?> getAllPageHome() {
        ResponseData responseData = new ResponseData();
        responseData.setData(foodService.getAllFoodsPageHome());
        responseData.setDesc("Lấy thành công danh sách food Page Home");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

//    @Cacheable("allFoods")
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        ResponseData responseData = new ResponseData();
        if (idCategory == 0) {
            responseData.setData(foodService.getAllFoods());
            responseData.setDesc("Lấy thành công danh sách food");
        } else {
            responseData.setData(foodService.getAllFoodsByIdCategory(idCategory));
            responseData.setDesc("Lấy thành công danh sách food by category");
            idCategory = 0;
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}

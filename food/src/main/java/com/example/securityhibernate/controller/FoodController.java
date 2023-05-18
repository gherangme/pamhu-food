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

    private int idCategory = 0;

    @Autowired
    private FoodService foodService;

    // Post Id Cate
    @PostMapping("/postIdCategory")
    public ResponseEntity<?> postIdCategory(@RequestParam int id) {
        if (id != 0) {
            idCategory = id;
            return new ResponseEntity<>(new ResponseData("Lấy thành công id"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData("Lấy thất bại id"), HttpStatus.BAD_REQUEST);
        }
    }

    // Get All Foods Page Home
    @GetMapping("/getAllPageHome")
    public ResponseEntity<?> getAllPageHome() {
        return new ResponseEntity<>(new ResponseData(foodService.getAllFoodsPageHome(),
                "Lấy thành công danh sách food Page Home"), HttpStatus.OK);
    }

    // Get All Foods
//    @Cacheable("allFoods")
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        ResponseData responseData = new ResponseData();

        // Get All Foods
        if (idCategory == 0) {
            responseData.setData(foodService.getAllFoods());
            responseData.setDesc("Lấy thành công danh sách food");

        // Get List Foods By Id Category
        } else {
            responseData.setData(foodService.getAllFoodsByIdCategory(idCategory));
            responseData.setDesc("Lấy thành công danh sách food by id category");
            idCategory = 0;
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}

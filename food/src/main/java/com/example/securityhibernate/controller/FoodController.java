package com.example.securityhibernate.controller;

import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.FoodService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foods")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodController {

    int idCategory = 0;
    int pageNumber = 1;

    @Autowired
    FoodService foodService;

    @PostMapping("/{id}")
    public ResponseEntity<?> getIdFood(@PathVariable int id) {
        if (id != 0) {
            idCategory = id;
            return new ResponseEntity<>(new ResponseData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{idPage}")
    public ResponseEntity<?> postPageNumber(@RequestParam int idPage) {
        pageNumber = idPage;
        return new ResponseEntity<>(new ResponseData(true), HttpStatus.OK);
    }

    @GetMapping("/getAllInformation")
    public ResponseEntity<?> getAllInformation() {
        return new ResponseEntity<>(new ResponseData(foodService.getAllFoodsPageHome()), HttpStatus.OK);
    }

//    @Cacheable("allFoods")
    @GetMapping("/getAllFoods")
    public ResponseEntity<?> getAllFoods() {
        ResponseData responseData = new ResponseData();

        if (idCategory == 0) {
            responseData.setData(foodService.getAllFoods(pageNumber));

        } else {
            responseData.setData(foodService.getAllFoodsByIdCategory(idCategory));
            idCategory = 0;
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}

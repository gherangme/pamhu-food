package com.example.securityhibernate.controller;

import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.FoodDetailService;
import com.example.securityhibernate.service.RatingFoodService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/food-details")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodDetailController {

    int idFood = 0;
    int idRes = 0;

    @Autowired
    FoodDetailService foodDetailService;

    @Autowired
    RatingFoodService ratingFoodService;

    @PostMapping("/{idFoodDetail}")
    public ResponseEntity<?> postIdFoodDetail(@PathVariable int idFoodDetail,
                                              @RequestParam int idResByUser) {
        if (idFoodDetail != 0) {
            idFood = idFoodDetail;
            idRes = idResByUser;
            return new ResponseEntity<>(new ResponseData(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllFoodDetails() {
        List<ResponseData> list = new ArrayList<>();
        if (idFood != 0) {
            list.add(new ResponseData(foodDetailService.getFoodById(idFood)));

            list.add(new ResponseData(ratingFoodService.getAllRatingFoodByIdFood(idFood)));

            list.add(new ResponseData(idRes));

        } else {
            list.add(new ResponseData(false));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}

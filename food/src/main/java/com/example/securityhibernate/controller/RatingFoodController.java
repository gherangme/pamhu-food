package com.example.securityhibernate.controller;

import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.RatingFoodService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rating-foods")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingFoodController {

    @Autowired
    RatingFoodService ratingFoodService;

    @Autowired
    JwtUtilsHelpers jwtUtilsHelpers;

    // Add New Rating Food
    @PostMapping("/postInforRatingFood")
    public ResponseEntity<?> postInforRatingFood(@RequestParam int id,
                                                  @RequestParam String comment,
                                                  @RequestParam int star,
                                                  @RequestParam String token) {
        String username = jwtUtilsHelpers.getUsernameByToken(token);
        boolean isSuccess= ratingFoodService.addRatingFood(username, id, star, comment);
        if (isSuccess) {
            return new ResponseEntity<>(new ResponseData(true,
                    "Cập nhật đánh giá thành công"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Cập nhật đánh giá thất bại"), HttpStatus.OK);
        }
    }

}

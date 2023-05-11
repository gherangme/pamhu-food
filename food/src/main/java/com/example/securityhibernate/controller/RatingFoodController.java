package com.example.securityhibernate.controller;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.RatingFoodService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rating-food")
public class RatingFoodController {

    @Autowired
    private RatingFoodService ratingFoodService;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @PostMapping("/postInforRatingFood")
    public ResponseEntity<?> postInforRatingFood(@RequestParam int id,
                                                  @RequestParam String comment,
                                                  @RequestParam int star,
                                                  @RequestParam String token) {
        System.out.println(id+comment+star+token);
        ResponseData responseData = new ResponseData();
        String username = jwtUtilsHelpers.getUsernameByToken(token);
        boolean isSuccess= ratingFoodService.addRatingFood(username, id, star, comment);
        if (isSuccess) {
            responseData.setData(true);
            responseData.setDesc("Cập nhật đánh giá thành công");
        } else {
            responseData.setData(false);
            responseData.setDesc("Cập nhật đánh giá thất bại");
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}

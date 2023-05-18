package com.example.securityhibernate.controller;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.FoodDetailService;
import com.example.securityhibernate.service.RatingFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/food-detail")
public class FoodDetailController {

    int idFood = 0;
    int idRes = 0;

    @Autowired
    private FoodDetailService foodDetailService;

    @Autowired
    private RatingFoodService ratingFoodService;

    // Post Id food Detail
    @PostMapping("/postIdFoodDetail")
    public ResponseEntity<?> postIdFoodDetail(@RequestParam int id,
                                              @RequestParam int idResByUser) {
        if (id != 0) {
            idFood = id;
            idRes = idResByUser;
            return new ResponseEntity<>(new ResponseData(true,
                    "Lấy thành công id food"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(true,
                    "Lấy thất bại id food",
                    400), HttpStatus.BAD_REQUEST);
        }
    }

    // Get Infor Food By Id
    @GetMapping("/getFoodById")
    public ResponseEntity<?> getFoodById() {
        List<ResponseData> list = new ArrayList<>();
        if (idFood != 0) {
            list.add(new ResponseData(foodDetailService.getFoodById(idFood),
                    "Lấy thành công thông tin food"));

            list.add(new ResponseData(ratingFoodService.getAllRatingFoodByIdFood(idFood),
                    "Lấy thành công thông tin đánh giá món ăn"));

            list.add(new ResponseData(idRes,
                    "Lấy thành công id res"));

        } else {
            list.add(new ResponseData(false,
                    "Lấy thất bại thông tin food",
                    400));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}

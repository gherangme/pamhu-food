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

    @PostMapping("/postIdFoodDetail")
    public ResponseEntity<?> postIdFoodDetail(@RequestParam int id, @RequestParam int idResByUser) {
        ResponseData responseData = new ResponseData();
        System.out.println("id food "+id+" idRes "+idResByUser);
        if (id != 0) {
            idFood = id;
            idRes = idResByUser;
            responseData.setDesc("Lấy thành công id food");
        } else {
            responseData.setDesc("Lấy thất bại id food");
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/GetFoodById")
    public ResponseEntity<?> GetFoodById() {
        List<ResponseData> list = new ArrayList<>();
        ResponseData responseData = new ResponseData();
        ResponseData responseData1 = new ResponseData();
        ResponseData responseData2 = new ResponseData();
        if (idFood != 0) {
            responseData.setData(foodDetailService.getFoodById(idFood));
            responseData.setDesc("Lấy thành công thông tin food");
            list.add(responseData);

            responseData1.setData(ratingFoodService.getAllRatingFoodByIdFood(idFood));
            responseData1.setDesc("Lấy thành công thông tin đánh giá món ăn");
            list.add(responseData1);

            responseData2.setData(idRes);
            responseData2.setDesc("Lấy thành công id res");
            list.add(responseData2);

        } else {
            responseData.setDesc("Lấy thất bại thông tin food");
            responseData.setStatusCode(400);
            list.add(responseData);

            responseData1.setDesc("Lấy thất bại thông tin đánh giá món ăn");
            responseData1.setStatusCode(400);
            list.add(responseData1);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}

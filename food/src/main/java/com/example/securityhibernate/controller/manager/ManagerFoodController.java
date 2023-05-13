package com.example.securityhibernate.controller.manager;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.LoginService;
import com.example.securityhibernate.service.ManagerFoodService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/manager/food")
public class ManagerFoodController {

    int idFoodDetail = 0;
    String token = null;

    @Autowired
    private ManagerFoodService managerFoodService;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @Autowired
    private LoginService loginService;

    // Post id food
    @PostMapping("/postIdFoodDetail")
    public ResponseEntity<?> postIdFoodDetail(@RequestParam int idFood, @RequestParam String tokenOfUser) {
        ResponseData responseData = new ResponseData();
        System.out.println(idFood+tokenOfUser);
        if (tokenOfUser != null) {
            idFoodDetail = idFood;
            token = tokenOfUser;
            responseData.setDesc("Lấy thành công thông tin id food, token");
        } else {
            responseData.setDesc("Lấy thất bại id food, token");
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Update Food
    @PutMapping("/updateFood")
    public ResponseEntity<?> updateFood(@RequestParam String name,
                                        @RequestParam double price,
                                        @RequestParam int idCate,
                                        @RequestParam MultipartFile file) {
        ResponseData responseData = new ResponseData();
        boolean isSucces = managerFoodService.updateFood(idFoodDetail, name, idCate ,price, file, token);

        if (isSucces) {
            responseData.setData(true);
            responseData.setDesc("Cập nhật thành công");
        } else {
            responseData.setData(false);
            responseData.setDesc("Cập nhật thất bại");
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Delete Food
    @DeleteMapping("/deleteFoodById")
    private ResponseEntity<?> deleteFoodById() {
        ResponseData responseData = new ResponseData();
        boolean isSucces = managerFoodService.deleteFoodById(idFoodDetail);

        if (isSucces) {
            responseData.setData(true);
            responseData.setDesc("Xoá thành công");
        } else {
            responseData.setData(false);
            responseData.setDesc("Xoá thất bại");
        }
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

    // Add Food
    @PostMapping("/addFood")
    public ResponseEntity<?> addFood(@RequestParam String name,
                                     @RequestParam double price,
                                     @RequestParam int idCate,
                                     @RequestParam MultipartFile file) {
        ResponseData responseData = new ResponseData();
        boolean isSuccess = managerFoodService.addFood(name, idCate, price, file, token);

        if (isSuccess) {
            responseData.setData(true);
            responseData.setDesc("Thêm thành công món ăn");
        } else {
            responseData.setData(true);
            responseData.setDesc("Thêm thất bại món ăn");
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Get Food Add
    @GetMapping("/getFoodAdd")
    public ResponseEntity<?> getFoodAdd() {
        List<ResponseData> list = new ArrayList<>();
        ResponseData responseData = new ResponseData();
        ResponseData responseData1 = new ResponseData();
        if (token != null) {

            // Set infor User
            responseData.setData(loginService.getFullNameByToken(token));
            list.add(responseData);

            // Set infor Food Add
            responseData1.setData(managerFoodService.getFoodAdd(jwtUtilsHelpers.getIdUserByToken(token)));
            list.add(responseData1);

        } else {
            responseData.setDesc("Lấy thất bại thông tin food detail");
            list.add(responseData);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Get Food Detail
    @GetMapping("/getFoodDetailById")
    public ResponseEntity<?> getFoodDetailById() {
        List<ResponseData> list = new ArrayList<>();
        ResponseData responseData = new ResponseData();
        ResponseData responseData1 = new ResponseData();
        if (token != null) {

            // Set Infor ManagerFood
            responseData.setData(managerFoodService.
                    getFoodDetailById(idFoodDetail, jwtUtilsHelpers.getIdUserByToken(token)));

            // Set infor User
            responseData1.setData(loginService.getFullNameByToken(token));

            list.add(responseData1);
            list.add(responseData);
        } else {
            responseData.setDesc("Lấy thất bại thông tin food detail");
            list.add(responseData);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}

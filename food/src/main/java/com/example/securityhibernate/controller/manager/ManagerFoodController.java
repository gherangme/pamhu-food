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

    private int idFoodDetail = 0;
    private String token = null;

    @Autowired
    private ManagerFoodService managerFoodService;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @Autowired
    private LoginService loginService;

    // Post id food
    @PostMapping("/postIdFoodDetail")
    public ResponseEntity<?> postIdFoodDetail(@RequestParam int idFood, @RequestParam String tokenOfUser) {
        if (tokenOfUser != null) {
            idFoodDetail = idFood;
            token = tokenOfUser;
            return new ResponseEntity<>(new ResponseData("Lấy thành công thông tin id food, token"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData("Lấy thất bại id food, token",
                    400), HttpStatus.BAD_REQUEST);
        }

    }

    // Update Food
    @PutMapping("/updateFood")
    public ResponseEntity<?> updateFood(@RequestParam String name,
                                        @RequestParam double price,
                                        @RequestParam int idCate,
                                        @RequestParam MultipartFile file) {
        boolean isSucces = managerFoodService.updateFood(idFoodDetail, name, idCate ,price, file, token);

        if (isSucces) {
            return new ResponseEntity<>(new ResponseData(true,
                    "Cập nhật thành công"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Cập nhật thất bại",
                    400), HttpStatus.OK);
        }
    }

    // Delete Food
    @DeleteMapping("/deleteFoodById")
    private ResponseEntity<?> deleteFoodById() {
        boolean isSucces = managerFoodService.deleteFoodById(idFoodDetail);

        if (isSucces) {
            return new ResponseEntity<>(new ResponseData(true,
                    "Xoá thành công"),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Xoá thất bại",
                    400),HttpStatus.BAD_REQUEST);
        }
    }

    // Add Food
    @PostMapping("/addFood")
    public ResponseEntity<?> addFood(@RequestParam String name,
                                     @RequestParam double price,
                                     @RequestParam int idCate,
                                     @RequestParam MultipartFile file) {
        boolean isSuccess = managerFoodService.addFood(name, idCate, price, file, token);

        if (isSuccess) {
            return new ResponseEntity<>(new ResponseData(true,
                    "Thêm thành công món ăn"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false,
                    "Thêm thất bại món ăn",
                    400), HttpStatus.BAD_REQUEST);
        }
    }

    // Get Food Add
    @GetMapping("/getFoodAdd")
    public ResponseEntity<?> getFoodAdd() {
        List<ResponseData> list = new ArrayList<>();
        if (token != null) {

            // Set infor User
            list.add(new ResponseData(loginService.getFullNameByToken(token),
                    "Lấy thành công thông tin user"));

            // Set infor Food Add
            list.add(new ResponseData(managerFoodService.getFoodAdd(jwtUtilsHelpers.getIdUserByToken(token)),
                    "Lấy thành công thông tin foodd"));

            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            list.add(new ResponseData(false,
                    "Lấy thất bại thông tin food detail",
                    400));

            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
        }
    }

    // Get Food Detail
    @GetMapping("/getFoodDetailById")
    public ResponseEntity<?> getFoodDetailById() {
        List<ResponseData> list = new ArrayList<>();
        if (token != null) {

            // Set infor User
            list.add(new ResponseData(loginService.getFullNameByToken(token)));

            // Set Infor ManagerFood
            list.add(new ResponseData(managerFoodService.getFoodDetailById(idFoodDetail, jwtUtilsHelpers.getIdUserByToken(token))));
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            list.add(new ResponseData(false,
                    "Lấy thất bại thông tin food detail",
                    400));
            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
        }
    }

}

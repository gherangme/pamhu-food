package com.example.securityhibernate.controller.manager;

import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.LoginService;
import com.example.securityhibernate.service.ManagerFoodService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/managers/foods")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManagerFoodController {

    @NonFinal
    int idFoodDetail = 0;
    @NonFinal
    String token = null;

    ManagerFoodService managerFoodService;
    JwtUtilsHelpers jwtUtilsHelpers;
    LoginService loginService;

    @GetMapping("/{idFood}")
    public ResponseEntity<?> getFood(@PathVariable int idFood, @RequestParam String tokenOfUser) {
        if (tokenOfUser != null) {
            idFoodDetail = idFood;
            token = tokenOfUser;
            return new ResponseEntity<>(new ResponseData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/updateFood")
    public ResponseEntity<?> updateFood(@RequestParam String name,
                                        @RequestParam double price,
                                        @RequestParam int idCate,
                                        @RequestParam MultipartFile file) {
        boolean isSucces = managerFoodService.updateFood(idFoodDetail, name, idCate ,price, file, token);

        if (isSucces) {
            return new ResponseEntity<>(new ResponseData(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteFood(@PathVariable int id) {
        boolean isSucces = managerFoodService.deleteFoodById(id);

        if (isSucces) {
            return new ResponseEntity<>(new ResponseData(true),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addFood")
    public ResponseEntity<?> createFood(@RequestParam String name,
                                     @RequestParam double price,
                                     @RequestParam int idCate,
                                     @RequestParam MultipartFile file) {
        boolean isSuccess = managerFoodService.addFood(name, idCate, price, file, token);
        if (isSuccess) {
            return new ResponseEntity<>(new ResponseData(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseData(false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllFoods")
    public ResponseEntity<?> getAllFoods() {
        List<ResponseData> list = new ArrayList<>();
        if (token != null) {
            list.add(new ResponseData(loginService.getFullNameByToken(token)));
            list.add(new ResponseData(managerFoodService.getFoodAdd(jwtUtilsHelpers.getIdUserByToken(token))));
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            list.add(new ResponseData(false));
            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getDetails")
    public ResponseEntity<?> getDetails() {
        List<ResponseData> list = new ArrayList<>();
        if (token != null) {

            // Set infor User
            list.add(new ResponseData(loginService.getFullNameByToken(token)));

            // Set Infor ManagerFood
            list.add(new ResponseData(managerFoodService.getFoodDetailById(idFoodDetail, jwtUtilsHelpers.getIdUserByToken(token))));
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            list.add(new ResponseData(false));
            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
        }
    }

}

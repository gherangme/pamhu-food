package com.example.securityhibernate.controller;

import com.example.securityhibernate.payload.ResponseData;
import com.example.securityhibernate.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAllPageHome")
    public ResponseEntity<?> getAllPageHome() {
        ResponseData responseData = new ResponseData();
        responseData.setData(categoryService.getAllPageHome());
        responseData.setDesc("Lấy thành công danh sách category Page Home");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        ResponseData responseData = new ResponseData();
        responseData.setData(categoryService.getAll());
        responseData.setDesc("Lấy thành công danh sách category");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}

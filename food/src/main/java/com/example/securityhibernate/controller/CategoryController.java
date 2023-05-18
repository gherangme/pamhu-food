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

    // Get all categories page home
    @GetMapping("/getAllPageHome")
    public ResponseEntity<?> getAllPageHome() {
        return new ResponseEntity<>(new ResponseData(categoryService.getAllPageHome(),
                "Lấy thành công danh sách category Page Home"), HttpStatus.OK);
    }

    // Get all categories
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(new ResponseData(categoryService.getAll(),
                "Lấy thành công danh sách category"), HttpStatus.OK);
    }

}

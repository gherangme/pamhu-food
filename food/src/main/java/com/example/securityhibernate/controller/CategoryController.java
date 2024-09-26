package com.example.securityhibernate.controller;

import com.example.securityhibernate.dto.response.ResponseData;
import com.example.securityhibernate.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return new ResponseEntity<>(new ResponseData(categoryService.getAllCategories()), HttpStatus.OK);
    }

    @GetMapping("/getAllInformation")
    public ResponseEntity<?> getAllInformation() {
        return new ResponseEntity<>(new ResponseData(categoryService.getAllPageHome()), HttpStatus.OK);
    }

}

package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.request.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllPageHome();
    List<CategoryDTO> getAllCategories();

}

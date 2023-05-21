package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.CategoryDTO;
import com.example.securityhibernate.entity.Category;
import com.example.securityhibernate.mapper.CategoryMapper;
import com.example.securityhibernate.repository.CategoryRepository;
import com.example.securityhibernate.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllPageHome() {
        List<Category> categoryList = categoryRepository.getAllPageHome();
        List<CategoryDTO> list = getAllCategoryCommon(categoryList);
        return list;
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDTO> list = getAllCategoryCommon(categoryList);
        return list;
    }

    // Common All Category
    private List<CategoryDTO> getAllCategoryCommon(List<Category> categoryList) {
        List<CategoryDTO> list = new ArrayList<>();
        for (Category category: categoryList) {
            list.add(categoryMapper.convertEntityToDTO(category));
        }
        return list;
    }
}

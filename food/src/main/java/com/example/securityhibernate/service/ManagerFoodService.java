package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.response.ManagerFoodDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ManagerFoodService {

    ManagerFoodDTO getFoodDetailById(int id, int idUser);
    boolean deleteFoodById(int id);
    boolean updateFood(int id, String name, int idCate, double price, MultipartFile file, String token);
    boolean addFood(String name, int idCate, double price, MultipartFile file, String token);
    ManagerFoodDTO getFoodAdd(int idUser);

}

package com.example.securityhibernate.repository;

import com.example.securityhibernate.dto.FoodDTO;
import com.example.securityhibernate.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    List<Food> findAll();

    List<Food> findByCategoryRestaurant_Id(int id);

    Food findById(int id);

    @Query(value = "SELECT * FROM food f \n" +
            "JOIN category_restaurant cr \n" +
            "ON cr.category_id = ?1 AND cr.id = f.cate_res_id", nativeQuery = true)
    List<Food> getAllByIdCategory(int id);

    @Query(value = "SELECT * FROM food f LIMIT 6", nativeQuery = true)
    List<Food> getAllPageHome();

}

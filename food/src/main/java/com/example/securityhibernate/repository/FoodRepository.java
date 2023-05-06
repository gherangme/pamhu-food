package com.example.securityhibernate.repository;

import com.example.securityhibernate.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    List<Food> findAll();
    Food findByCategoryRestaurant_Id(int id);
    Food findById(int id);

}

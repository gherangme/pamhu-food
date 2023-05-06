package com.example.securityhibernate.repository;

import com.example.securityhibernate.entity.CategoryRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRestaurantRepository extends JpaRepository<CategoryRestaurant, Integer> {

    List<CategoryRestaurant> findAllByRestaurant_Id(int idRes);

}

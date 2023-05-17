package com.example.securityhibernate.repository;

import com.example.securityhibernate.entity.Category;
import com.example.securityhibernate.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT * FROM category c LIMIT 6", nativeQuery = true)
    List<Category> getAllPageHome();

    Category findById(int idCate);

}

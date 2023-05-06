package com.example.securityhibernate.repository;

import com.example.securityhibernate.entity.RatingFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingFoodRepository extends JpaRepository<RatingFood, Integer> {

    List<RatingFood> findAllByFood_Id(int id);

}

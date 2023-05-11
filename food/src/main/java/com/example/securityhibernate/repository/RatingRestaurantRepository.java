package com.example.securityhibernate.repository;

import com.example.securityhibernate.entity.RatingRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRestaurantRepository extends JpaRepository<RatingRestaurant, Integer> {

    List<RatingRestaurant> findByRestaurant_Id(int id);

}

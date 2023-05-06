package com.example.securityhibernate.repository;

import com.example.securityhibernate.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> findAll();

    Restaurant findById(int id);

}

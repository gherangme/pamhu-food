package com.example.securityhibernate.repository;

import com.example.securityhibernate.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query(value = "SELECT * FROM restaurant r\n" +
            "LEFT JOIN rating_restaurant rr ON r.id = rr.res_id\n" +
            "ORDER BY rr.star DESC LIMIT 6", nativeQuery = true)
    List<Restaurant> findAllPageHome();

    List<Restaurant> findAll();

    Restaurant findById(int id);

    Restaurant findByUsers_Id(int id);
}

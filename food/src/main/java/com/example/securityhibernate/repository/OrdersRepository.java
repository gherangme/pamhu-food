package com.example.securityhibernate.repository;

import com.example.securityhibernate.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    Orders findById(int id);
    Orders findByStatus_IdAndUsers_Username(int status, String username);
    Orders findByStatus_IdAndUsers_UsernameAndRestaurant_Id(int idStatus, String username, int idRes);

}

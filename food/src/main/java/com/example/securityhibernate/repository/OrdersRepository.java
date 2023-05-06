package com.example.securityhibernate.repository;

import com.example.securityhibernate.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    Orders findById(int id);
    Orders findByStatusAndUsers_Username(String status, String username);

}

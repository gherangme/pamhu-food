package com.example.securityhibernate.repository;

import com.example.securityhibernate.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    Orders findById(int id);
    List<Orders> findByRestaurant_IdAndStatus_Id(int idRes, int idStatus);
    Orders findByStatus_IdAndUsers_Username(int status, String username);
    Orders findByStatus_IdAndUsers_UsernameAndRestaurant_Id(int idStatus, String username, int idRes);
    List<Orders> findByStatus_IdAndUsers_Id(int idStatus, int idUser);

}

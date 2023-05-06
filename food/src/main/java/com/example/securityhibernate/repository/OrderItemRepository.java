package com.example.securityhibernate.repository;

import com.example.securityhibernate.entity.OrderItem;
import com.example.securityhibernate.entity.keys.KeyOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, KeyOrderItem> {
    OrderItem findByFood_IdAndOrders_Id(int idFood, int idOrder);
    List<OrderItem> findByOrders_Id(int idOrder);
}

package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.entity.OrderItem;
import com.example.securityhibernate.entity.Orders;
import com.example.securityhibernate.entity.Users;
import com.example.securityhibernate.entity.keys.KeyOrderItem;
import com.example.securityhibernate.repository.FoodRepository;
import com.example.securityhibernate.repository.OrderItemRepository;
import com.example.securityhibernate.repository.OrdersRepository;
import com.example.securityhibernate.repository.UserRepository;
import com.example.securityhibernate.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public boolean saveOrder(String username, int idFood, int amount, double price) {

        try {
            // Lấy ngày hiện tại
            LocalDate currentDate = LocalDate.now();

            // Chuyển đổi từ LocalDate sang Date
            Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Orders orders = ordersRepository.findByStatus_IdAndUsers_Username(1, username);

            // check exist 1
            if (orders == null) {
                Orders orders1 = new Orders();
                orders1.setCreateDate(date);
//                orders1.setStatus("1"); // 1. Ordering, 2. Ordered, ...
                orders1.setUsers(userRepository.findByUsername(username));
                // set total price sau

                // save order
                orders1 = ordersRepository.save(orders1);

                // save order item
                saveOrderItem(orders1, idFood, price, amount);
                return true;
            } else {
                // save order item
                saveOrderItem(orders, idFood, price, amount);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error saveOrder "+e.getMessage());
            return false;
        }
    }

    public void saveOrderItem(Orders orders, int idFood, double price, int amount) {
        OrderItem orderItem = new OrderItem();
        KeyOrderItem keyOrderItem = new KeyOrderItem();
        keyOrderItem.setFoodId(idFood);
        keyOrderItem.setOrderId(orders.getId());

        orderItem.setKeys(keyOrderItem);
        orderItem.setOrders(orders);
        orderItem.setFood(foodRepository.findById(idFood));
        orderItem.setPrice(price);
        orderItem.setAmount(amount);

        orderItemRepository.save(orderItem);
    }
}

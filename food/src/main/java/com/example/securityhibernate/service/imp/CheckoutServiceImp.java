package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.CheckoutDTO;
import com.example.securityhibernate.dto.FoodDTO;
import com.example.securityhibernate.dto.UserDTO;
import com.example.securityhibernate.entity.*;
import com.example.securityhibernate.repository.*;
import com.example.securityhibernate.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutServiceImp implements CheckoutService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public UserDTO getUserByUsername(String username) {
        Users users = userRepository.findByUsername(username);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(users.getId());
        userDTO.setUsername(users.getUsername());
        userDTO.setFullName(users.getFullname());
        userDTO.setAddress(users.getAddress());
        userDTO.setPhone(users.getPhone());

        return userDTO;
    }

    @Override
    public int checkout(CheckoutDTO checkoutDTO) {
        try {
            // Set thông tin user thay đổi trong checkout
            Users users = userRepository.findByUsername(checkoutDTO.getUsername());
            users.setFullname(checkoutDTO.getFullName());
            users.setAddress(checkoutDTO.getAddress());
            users.setPhone(checkoutDTO.getPhone());
            userRepository.save(users);

            // Set status khi order thành công
            Orders orders = ordersRepository.findByStatus_IdAndUsers_Username(1, checkoutDTO.getUsername());
            orders.setTotalPrice(checkoutDTO.getTotalPrice());
            Status status = statusRepository.findById(2);
            orders.setStatus(status);

            ordersRepository.save(orders);

            return orders.getId();
        } catch (Exception e) {
            System.out.println("Error checkout "+e.getMessage());
            return 0;
        }
    }

    @Override
    public List<FoodDTO> getListFoodCheckout(String username) {
        Orders orders = ordersRepository.findByStatus_IdAndUsers_Username(1, username);
        List<OrderItem> list = orderItemRepository.findByOrders_Id(orders.getId());
        List<FoodDTO> dtoList = new ArrayList<>();

        for (OrderItem orderItem: list) {
            Food food = foodRepository.findById(orderItem.getFood().getId());
            FoodDTO foodDTO = new FoodDTO();
            foodDTO.setName(food.getName());
            foodDTO.setAmount(orderItem.getAmount());
            foodDTO.setPrice(orderItem.getPrice());

            dtoList.add(foodDTO);
        }
        return dtoList;
    }

}

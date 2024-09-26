package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.response.CheckoutDTO;
import com.example.securityhibernate.dto.request.FoodDTO;
import com.example.securityhibernate.dto.request.UserDTO;
import com.example.securityhibernate.entity.*;
import com.example.securityhibernate.repository.*;
import com.example.securityhibernate.service.CheckoutService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckoutServiceImp implements CheckoutService {

    UserRepository userRepository;
    OrdersRepository ordersRepository;
    StatusRepository statusRepository;
    OrderItemRepository orderItemRepository;
    FoodRepository foodRepository;

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
            Users users = userRepository.findByUsername(checkoutDTO.getUsername());
            users.setFullname(checkoutDTO.getFullName());
            users.setAddress(checkoutDTO.getAddress());
            users.setPhone(checkoutDTO.getPhone());
            userRepository.save(users);

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

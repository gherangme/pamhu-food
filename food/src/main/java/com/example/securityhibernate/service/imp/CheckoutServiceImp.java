package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.CheckoutDTO;
import com.example.securityhibernate.dto.UserDTO;
import com.example.securityhibernate.entity.Orders;
import com.example.securityhibernate.entity.Users;
import com.example.securityhibernate.repository.OrderItemRepository;
import com.example.securityhibernate.repository.OrdersRepository;
import com.example.securityhibernate.repository.UserRepository;
import com.example.securityhibernate.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImp implements CheckoutService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrdersRepository ordersRepository;

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

            Orders orders = ordersRepository.findByStatusAndUsers_Username("1", checkoutDTO.getUsername());
            System.out.println(orders);
            orders.setTotalPrice(checkoutDTO.getTotalPrice());
            orders.setStatus("2");
            ordersRepository.save(orders);

            return orders.getId();
        } catch (Exception e) {
            System.out.println("Error checkout "+e.getMessage());
            return 0;
        }
    }
}

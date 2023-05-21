package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.FoodDTO;
import com.example.securityhibernate.dto.InvoiceDTO;
import com.example.securityhibernate.entity.OrderItem;
import com.example.securityhibernate.entity.Orders;
import com.example.securityhibernate.entity.Users;
import com.example.securityhibernate.repository.*;
import com.example.securityhibernate.service.ManagerInvoiceService;
import com.example.securityhibernate.utils.FormatDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerInvoiceServiceImp implements ManagerInvoiceService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public InvoiceDTO getInforInvoiceById(int id) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();

        // Set Order
        Orders orders = ordersRepository.findById(id);
        invoiceDTO.setId(orders.getId());
        invoiceDTO.setDate(new FormatDate().formatDate(orders.getCreateDate()));
        invoiceDTO.setTotalPrice(orders.getTotalPrice());

        // Set List Food
        List<FoodDTO> foodDTOList = new ArrayList<>();
        List<OrderItem> list = orderItemRepository.findByOrders_Id(id);
        for (OrderItem orderItem: list) {
            FoodDTO foodDTO = new FoodDTO();
            foodDTO.setName(foodRepository.findById(orderItem.getFood().getId()).getName());
            foodDTO.setAmount(orderItem.getAmount());
            foodDTO.setPrice(orderItem.getFood().getPrice());
            foodDTOList.add(foodDTO);
        }
        invoiceDTO.setFoodDTOList(foodDTOList);

        // Set User
        Users users = userRepository.findByUsername(orders.getUsers().getUsername());
        invoiceDTO.setName(users.getFullname());
        invoiceDTO.setAddress(users.getAddress());
        invoiceDTO.setPhone(users.getPhone());

        // Set coupon
        try {
            invoiceDTO.setVoucher(couponRepository.findById(orders.getCoupon().getId()).getVoucher());
        } catch (Exception e) {
            invoiceDTO.setVoucher(0);
        }

        return invoiceDTO;
    }
}

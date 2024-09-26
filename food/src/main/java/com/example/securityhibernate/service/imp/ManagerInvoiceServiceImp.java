package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.request.FoodDTO;
import com.example.securityhibernate.dto.response.InvoiceDTO;
import com.example.securityhibernate.entity.OrderItem;
import com.example.securityhibernate.entity.Orders;
import com.example.securityhibernate.entity.Users;
import com.example.securityhibernate.repository.*;
import com.example.securityhibernate.service.ManagerInvoiceService;
import com.example.securityhibernate.utils.FormatDate;
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
public class ManagerInvoiceServiceImp implements ManagerInvoiceService {

    OrdersRepository ordersRepository;
    OrderItemRepository orderItemRepository;
    FoodRepository foodRepository;
    UserRepository userRepository;
    CouponRepository couponRepository;

    @Override
    public InvoiceDTO getInvoiceById(int id) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();

        Orders orders = ordersRepository.findById(id);
        invoiceDTO.setId(orders.getId());
        invoiceDTO.setDate(new FormatDate().formatDate(orders.getCreateDate()));
        invoiceDTO.setTotalPrice(orders.getTotalPrice());

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

        Users users = userRepository.findByUsername(orders.getUsers().getUsername());
        invoiceDTO.setName(users.getFullname());
        invoiceDTO.setAddress(users.getAddress());
        invoiceDTO.setPhone(users.getPhone());

        try {
            invoiceDTO.setVoucher(couponRepository.findById(orders.getCoupon().getId()).getVoucher());
        } catch (Exception e) {
            invoiceDTO.setVoucher(0);
        }

        return invoiceDTO;
    }
}

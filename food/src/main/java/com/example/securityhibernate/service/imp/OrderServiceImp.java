package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.*;
import com.example.securityhibernate.entity.*;
import com.example.securityhibernate.entity.keys.KeyOrderItem;
import com.example.securityhibernate.repository.*;
import com.example.securityhibernate.service.OrderService;
import com.example.securityhibernate.utils.FormatDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<OrderDetailDTO> getAllOrderByIdUser(int idUser) {
        List<OrderDetailDTO> list = new ArrayList<>();
        List<Orders> ordersList = ordersRepository.findByStatus_IdAndUsers_Id(2, idUser);
        for (Orders orders : ordersList) {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setId(orders.getId());
            orderDetailDTO.setTotalPrice(orders.getTotalPrice());
            orderDetailDTO.setDate(new FormatDate().formatDate(orders.getCreateDate()));

            // Set restaurant
            Restaurant restaurant = restaurantRepository.findById(orders.getRestaurant().getId());
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setId(restaurant.getId());
            restaurantDTO.setName(restaurant.getName());
            orderDetailDTO.setRestaurantDTO(restaurantDTO);

            // Set coupon
            try {
                Coupon coupon = couponRepository.findById(orders.getCoupon().getId());
                CouponDTO couponDTO = new CouponDTO();
                couponDTO.setId(coupon.getId());
                couponDTO.setName(coupon.getName());
                couponDTO.setVoucher(coupon.getVoucher());
                orderDetailDTO.setCouponDTO(couponDTO);
            } catch (Exception e) {

            }

            // Set list food
            List<FoodDTO> foodDTOList = new ArrayList<>();
            List<OrderItem> orderItemList = orderItemRepository.findByOrders_Id(orders.getId());
            for (OrderItem orderItem : orderItemList) {
                FoodDTO foodDTO = new FoodDTO();
                foodDTO.setName(foodRepository.findById(orderItem.getFood().getId()).getName());
                foodDTO.setAmount(orderItem.getAmount());
                foodDTO.setPrice(orderItem.getFood().getPrice());
                foodDTOList.add(foodDTO);
            }
            orderDetailDTO.setFoodDTOList(foodDTOList);

            list.add(orderDetailDTO);
        }
        return list;
    }

    @Override
    public boolean saveOrder(String username, int idFood, int amount, double price) {

        try {
            // Lấy ngày hiện tại
            LocalDate currentDate = LocalDate.now();

            // Chuyển đổi từ LocalDate sang Date
            Date date = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Orders orders = ordersRepository.findByStatus_IdAndUsers_Username(1, username);

            // check exist orders
            if (orders == null) {
                Orders orders1 = new Orders();
                orders1.setCreateDate(date);
                orders1.setUsers(userRepository.findByUsername(username));

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
            System.out.println("Error saveOrder " + e.getMessage());
            return false;
        }
    }

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
        for (OrderItem orderItem : list) {
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

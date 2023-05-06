package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.CategoryDTO;
import com.example.securityhibernate.dto.FoodDTO;
import com.example.securityhibernate.entity.Food;
import com.example.securityhibernate.entity.OrderItem;
import com.example.securityhibernate.entity.Orders;
import com.example.securityhibernate.repository.FoodRepository;
import com.example.securityhibernate.repository.OrderItemRepository;
import com.example.securityhibernate.repository.OrdersRepository;
import com.example.securityhibernate.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public List<FoodDTO> getListFoods(int idFood, String username) {
        List<FoodDTO> list = new ArrayList<>();

        Orders orders = ordersRepository.findByStatusAndUsers_Username("1", username);
        if (orders != null) {
            OrderItem orderItem = orderItemRepository.findByFood_IdAndOrders_Id(idFood, orders.getId());
            List<OrderItem> orderItemList = orderItemRepository.findByOrders_Id(orders.getId());

            Food food = foodRepository.findById(idFood);
            FoodDTO foodDTO = new FoodDTO();
            if (orderItem != null) {
                setFoodDTO(food, foodDTO, orderItem.getAmount());
                list.add(foodDTO);

                for (OrderItem orderItem1: orderItemList) {
                    if (orderItem1.getFood().getId() != idFood) {
                        food = foodRepository.findById(orderItem1.getFood().getId());
                        FoodDTO foodDTO1 = new FoodDTO();
                        setFoodDTO(food, foodDTO1, orderItem1.getAmount());
                        list.add(foodDTO1);
                    }
                }
            } else {
                setFoodDTO(food, foodDTO, 1);
                list.add(foodDTO);

                for (OrderItem orderItem1: orderItemList) {
                    if (orderItem1.getFood().getId() != idFood) {
                        food = foodRepository.findById(orderItem1.getFood().getId());
                        FoodDTO foodDTO1 = new FoodDTO();
                        setFoodDTO(food, foodDTO1, orderItem1.getAmount());
                        list.add(foodDTO1);
                    }
                }
            }

        } else {
            Food food = foodRepository.findById(idFood);
            FoodDTO foodDTO = new FoodDTO();
            setFoodDTO(food, foodDTO, 1);
            list.add(foodDTO);
        }

        return list;
    }

    @Override
    public boolean deleteItemOder(int idFood, String username) {
        Orders orders = ordersRepository.findByStatusAndUsers_Username("1", username);
        if (orders != null) {
            OrderItem orderItem = orderItemRepository.findByFood_IdAndOrders_Id(idFood, orders.getId());
            try {
                orderItemRepository.delete(orderItem);
                return true;
            } catch (Exception e) {
                System.out.println("Error deleteItemOder "+e.getMessage());
                return false;
            }
        }

        return false;
    }

    public void setFoodDTO(Food food, FoodDTO foodDTO, int amount) {
        foodDTO.setId(food.getId());
        foodDTO.setImage(food.getImage());
        foodDTO.setName(food.getName());
        foodDTO.setPrice(food.getPrice());
        foodDTO.setAmount(amount);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(food.getCategoryRestaurant().getCategory().getId());
        categoryDTO.setName(food.getCategoryRestaurant().getCategory().getName());
        foodDTO.setCategoryDTO(categoryDTO);
    }
}
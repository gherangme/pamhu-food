package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.CategoryDTO;
import com.example.securityhibernate.dto.FoodDTO;
import com.example.securityhibernate.entity.*;
import com.example.securityhibernate.entity.keys.KeyOrderItem;
import com.example.securityhibernate.repository.*;
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
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<FoodDTO> addFoodToCart(int idFood, int idRes, String username) {
        List<FoodDTO> list = new ArrayList<>();

        Orders orders = ordersRepository.findByStatus_IdAndUsers_Username(1, username);

        // Check tồn tại order
        if (orders != null) {
            OrderItem orderItem = orderItemRepository.findByFood_IdAndOrders_Id(idFood, orders.getId());
            List<OrderItem> orderItemList = orderItemRepository.findByOrders_Id(orders.getId());

            Food food = foodRepository.findById(idFood);
            FoodDTO foodDTO = new FoodDTO();

            // Check idRes có giống idRes trong order hay không
            if (orders.getRestaurant().getId() == idRes) {

                // Check orderItem tồn tại
                if (orderItem != null) {

                    // Check food có tồn tại trong orderItem hay không
                    if (food != null) {
                        setFoodDTO(food, foodDTO, orderItem.getAmount());
                        list.add(foodDTO);
                    }

                    // Lấy thông tin orderItem
                    for (OrderItem orderItem1 : orderItemList) {
                        if (orderItem1.getFood().getId() != idFood) {
                            food = foodRepository.findById(orderItem1.getFood().getId());
                            FoodDTO foodDTO1 = new FoodDTO();
                            setFoodDTO(food, foodDTO1, orderItem1.getAmount());
                            list.add(foodDTO1);
                        }
                    }
                } else {

                    // Thêm food mới
                    if (food != null) {
                        setFoodDTO(food, foodDTO, 1);
                        list.add(foodDTO);
                    }

                    // Lấy thông tin orderItem
                    for (OrderItem orderItem1 : orderItemList) {
                        if (orderItem1.getFood().getId() != idFood) {
                            food = foodRepository.findById(orderItem1.getFood().getId());
                            FoodDTO foodDTO1 = new FoodDTO();
                            setFoodDTO(food, foodDTO1, orderItem1.getAmount());
                            list.add(foodDTO1);
                        }
                    }
                }

            // Check cart when idRes, idFood = 0
            } else if (idRes == 0) {
                for (OrderItem orderItem1: orderItemList) {
                    try {
                        Food food1 = foodRepository.findById(orderItem1.getFood().getId());
                        FoodDTO foodDTO1 = new FoodDTO();
                        setFoodDTO(food1, foodDTO1, orderItem1.getAmount());
                        list.add(foodDTO);
                    } catch (Exception e) {
                        return null;
                    }
                }
            } else {

                // Kiểm tra orderItem có đang chứa food của nhà hàng khác hay không
                if (orderItemRepository.findByOrders_Id(orders.getId()).size() > 0) {
                    return null;
                } else {
                    orders.setRestaurant(restaurantRepository.findById(idRes));
                    ordersRepository.save(orders);
                    if (food != null) {
                        setFoodDTO(food, foodDTO, 1);
                        list.add(foodDTO);
                    }
                }
            }

        } else {

            // Tạo Order và OrderItem mới
            if (idRes != 0) {

                // Create Orders
                Orders order = new Orders();
                order.setUsers(userRepository.findByUsername(username));
                order.setStatus(statusRepository.findById(1));
                order.setRestaurant(restaurantRepository.findById(idRes));
                ordersRepository.save(order);

                // Create Order Item
                OrderItem orderItem = new OrderItem();
                Food food = foodRepository.findById(idFood);
                orderItem.setFood(food);
                orderItem.setOrders(order);
                orderItem.setAmount(1);
                orderItem.setPrice(food.getPrice());
                KeyOrderItem keyOrderItem = new KeyOrderItem();
                keyOrderItem.setOrderId(order.getId());
                keyOrderItem.setFoodId(idFood);
                orderItem.setKeys(keyOrderItem);
                orderItemRepository.save(orderItem);

                FoodDTO foodDTO = new FoodDTO();
                setFoodDTO(food, foodDTO, 1);
                list.add(foodDTO);

            // Check cart when idRes, idFood = 0
            } else {
                return null;
            }
        }

        return list;
    }

    // Delete orderItem
    @Override
    public boolean deleteItemOder(int idFood, String username) {
        Orders orders = ordersRepository.findByStatus_IdAndUsers_Username(1, username);
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

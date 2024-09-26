package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.request.CategoryDTO;
import com.example.securityhibernate.dto.request.CouponDTO;
import com.example.securityhibernate.dto.request.FoodDTO;
import com.example.securityhibernate.dto.request.RestaurantDetailDTO;
import com.example.securityhibernate.dto.response.ManagerDTO;
import com.example.securityhibernate.dto.response.OrderDetailDTO;
import com.example.securityhibernate.entity.*;
import com.example.securityhibernate.repository.*;
import com.example.securityhibernate.service.ManagerService;
import com.example.securityhibernate.utils.FormatDate;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
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
public class ManagerServiceImp implements ManagerService {

    private RestaurantRepository restaurantRepository;
    private RatingRestaurantRepository ratingRestaurantRepository;
    private OrdersRepository ordersRepository;
    private CategoryRestaurantRepository categoryRestaurantRepository;
    private FoodRepository foodRepository;
    private CategoryRepository categoryRepository;
    private CouponRepository couponRepository;
    private JwtUtilsHelpers jwtUtilsHelpers;
    private UserRepository userRepository;

    @Override
    public List<OrderDetailDTO> getAllOrders(String token) {
        List<OrderDetailDTO> dtoList = new ArrayList<>();
        Restaurant restaurant = restaurantRepository.findByUsers_Id(userRepository
                .findByUsername(jwtUtilsHelpers.getUsernameByToken(token))
                .getId());

        List<Orders> list = ordersRepository.findByRestaurant_IdAndStatus_Id(restaurant.getId(), 2);
        for (Orders orders: list) {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setId(orders.getId());
            orderDetailDTO.setTotalPrice(orders.getTotalPrice());

            FormatDate formatDate = new FormatDate();
            orderDetailDTO.setDate(formatDate.formatDate(orders.getCreateDate()));
            dtoList.add(orderDetailDTO);
        }

        return dtoList;
    }

    @Override
    public ManagerDTO getDashboardManager(int idUser) {
        ManagerDTO managerDTO = new ManagerDTO();

        Restaurant restaurant = restaurantRepository.findByUsers_Id(idUser);
        RestaurantDetailDTO restaurantDetailDTO = new RestaurantDetailDTO();
        restaurantDetailDTO.setName(restaurant.getName());
        restaurantDetailDTO.setAddress(restaurant.getAddress());
        restaurantDetailDTO.setImage(restaurant.getImage());
        restaurantDetailDTO.setDesc(restaurant.getDesc());

        List<RatingRestaurant> ratingRestaurant = ratingRestaurantRepository.findByRestaurant_Id(restaurant.getId());
        float starRes = 0;
        for (RatingRestaurant ratingRestaurant1: ratingRestaurant) {
            starRes += ratingRestaurant1.getStar();
        }
        restaurantDetailDTO.setRating(starRes / ratingRestaurant.size());
        managerDTO.setRestaurantDetailDTO(restaurantDetailDTO);

        Coupon coupon = couponRepository.findById(restaurant.getCoupon().getId());
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setId(coupon.getId());
        couponDTO.setName(coupon.getName());
        couponDTO.setVoucher(coupon.getVoucher());
        managerDTO.setCouponDTO(couponDTO);

        List<Orders> list = ordersRepository.findByRestaurant_IdAndStatus_Id(restaurant.getId(), 2);
        if (list.size() > 0) {
            double totalIncome = 0;
            for (Orders orders : list) {
                totalIncome += orders.getTotalPrice();
            }
            managerDTO.setTotalIncome(totalIncome);
        } else {
            managerDTO.setTotalIncome(0);
        }

        managerDTO.setTotalOrders(list.size());

        List<CategoryRestaurant> list1 = categoryRestaurantRepository
                .findAllByRestaurant_Id(restaurant.getId());
        managerDTO.setTotalCategorys(list1.size());

        List<FoodDTO> list2 = new ArrayList<>();
        List<CategoryDTO> list3 = new ArrayList<>();
        for (CategoryRestaurant categoryRestaurant: list1) {
            List<Food> foodList = foodRepository.findByCategoryRestaurant_Id(categoryRestaurant.getId());
            for (Food food: foodList) {
                FoodDTO foodDTO = new FoodDTO();
                foodDTO.setId(food.getId());
                foodDTO.setName(food.getName());
                foodDTO.setImage(food.getImage());
                foodDTO.setPrice(food.getPrice());

                CategoryDTO categoryDTO = new CategoryDTO();
                Category category = categoryRepository.findById(food.getCategoryRestaurant().getCategory().getId());
                categoryDTO.setId(category.getId());
                categoryDTO.setName(category.getName());
                foodDTO.setCategoryDTO(categoryDTO);

                if (!list3.contains(categoryDTO)) {
                    list3.add(categoryDTO);
                }

                list2.add(foodDTO);
            }
        }
        managerDTO.setTotalFoods(list2.size());

        managerDTO.setFoodDTOList(list2);

        managerDTO.setCategoryDTOList(list3);

        return managerDTO;
    }
}

package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.*;
import com.example.securityhibernate.entity.*;
import com.example.securityhibernate.repository.*;
import com.example.securityhibernate.service.ManagerService;
import com.example.securityhibernate.utils.FormatDate;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerServiceImp implements ManagerService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RatingRestaurantRepository ratingRestaurantRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CategoryRestaurantRepository categoryRestaurantRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<OrderDetailDTO> getAllOrder(String token) {
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
    public ManagerDTO getInforDashboardManager(int idUser) {
        ManagerDTO managerDTO = new ManagerDTO();

        // Set Infor Res
        Restaurant restaurant = restaurantRepository.findByUsers_Id(idUser);
        RestaurantDetailDTO restaurantDetailDTO = new RestaurantDetailDTO();
        restaurantDetailDTO.setName(restaurant.getName());
        restaurantDetailDTO.setAddress(restaurant.getAddress());
        restaurantDetailDTO.setImage(restaurant.getImage());
        restaurantDetailDTO.setDesc(restaurant.getDesc());

        // Set Rating Res
        List<RatingRestaurant> ratingRestaurant = ratingRestaurantRepository.findByRestaurant_Id(restaurant.getId());
        float starRes = 0;
        for (RatingRestaurant ratingRestaurant1: ratingRestaurant) {
            starRes += ratingRestaurant1.getStar();
        }
        restaurantDetailDTO.setRating(starRes / ratingRestaurant.size());
        managerDTO.setRestaurantDetailDTO(restaurantDetailDTO);

        // Set Coupon of Res
        Coupon coupon = couponRepository.findById(restaurant.getCoupon().getId());
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setId(coupon.getId());
        couponDTO.setName(coupon.getName());
        couponDTO.setVoucher(coupon.getVoucher());
        managerDTO.setCouponDTO(couponDTO);

        // Set Total Income
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

        // Set Total Orders
        managerDTO.setTotalOrders(list.size());

        // Set Total Category of Res
        List<CategoryRestaurant> list1 = categoryRestaurantRepository
                .findAllByRestaurant_Id(restaurant.getId());
        managerDTO.setTotalCategorys(list1.size());

        // Set Total Foods of Res
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

                // Kiểm tra chưa tồn tại thì thêm vào
                if (!list3.contains(categoryDTO)) {
                    list3.add(categoryDTO);
                }

                list2.add(foodDTO);
            }
        }
        managerDTO.setTotalFoods(list2.size());

        // Set List Foods of Res
        managerDTO.setFoodDTOList(list2);

        // Set List Cate of Res
        managerDTO.setCategoryDTOList(list3);

        return managerDTO;
    }
}

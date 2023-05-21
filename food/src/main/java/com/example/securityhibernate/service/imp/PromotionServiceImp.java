package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.dto.CouponDTO;
import com.example.securityhibernate.dto.PromotionDTO;
import com.example.securityhibernate.entity.Coupon;
import com.example.securityhibernate.entity.Orders;
import com.example.securityhibernate.entity.Restaurant;
import com.example.securityhibernate.mapper.CouponMapper;
import com.example.securityhibernate.repository.CouponRepository;
import com.example.securityhibernate.repository.OrdersRepository;
import com.example.securityhibernate.repository.RestaurantRepository;
import com.example.securityhibernate.service.PromotionService;
import com.example.securityhibernate.utils.JwtUtilsHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionServiceImp implements PromotionService {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private JwtUtilsHelpers jwtUtilsHelpers;

    @Override
    public double checkPromotionCode(String promotionCode, int idRes, String username) {
        try {
            Coupon coupon = couponRepository.findByName(promotionCode);
            if (coupon.getId() == restaurantRepository.findById(idRes).getCoupon().getId()) {
                Orders orders = ordersRepository.findByStatus_IdAndUsers_UsernameAndRestaurant_Id(1, username, idRes);
                orders.setCoupon(coupon);
                ordersRepository.save(orders);
                return coupon.getVoucher();
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error checkPromotionCode "+e.getMessage());
            return 0;
        }
    }

    @Override
    public CouponDTO getPromotionById(int id) {
        Coupon coupon = couponRepository.findById(id);
        return couponMapper.convertEntityToDTO(coupon);
    }

    @Override
    public List<CouponDTO> getAllPromotion() {
        List<CouponDTO> list = new ArrayList<>();
        List<Coupon> couponList = couponRepository.findAll();
        for (Coupon coupon: couponList) {
            list.add(couponMapper.convertEntityToDTO(coupon));
        }
        return list;
    }

    @Override
    public boolean updatePromotion(int id, String token) {
        try {
            Restaurant restaurant = restaurantRepository.findByUsers_Id(jwtUtilsHelpers.getIdUserByToken(token));
            restaurant.setCoupon(couponRepository.findById(id));
            restaurantRepository.save(restaurant);
            return true;
        } catch (Exception e) {
            System.out.println("Error updatePromotion "+e.getMessage());
            return false;
        }
    }
}

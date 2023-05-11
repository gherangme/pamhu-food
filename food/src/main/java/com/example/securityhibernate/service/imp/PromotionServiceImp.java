package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.entity.Coupon;
import com.example.securityhibernate.entity.Orders;
import com.example.securityhibernate.entity.Restaurant;
import com.example.securityhibernate.repository.CouponRepository;
import com.example.securityhibernate.repository.OrdersRepository;
import com.example.securityhibernate.repository.RestaurantRepository;
import com.example.securityhibernate.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImp implements PromotionService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrdersRepository ordersRepository;

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
}

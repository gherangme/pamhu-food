package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.request.CouponDTO;

import java.util.List;

public interface PromotionService {

    double checkPromotionCode(String promotionCode, int idRes, String username);
    CouponDTO getPromotionById(int id);
    List<CouponDTO> getAllPromotions();
    boolean updatePromotion(int id, String token);

}

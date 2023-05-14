package com.example.securityhibernate.service;

import com.example.securityhibernate.dto.CouponDTO;
import com.example.securityhibernate.dto.PromotionDTO;

import java.util.List;

public interface PromotionService {

    double checkPromotionCode(String promotionCode, int idRes, String username);
    CouponDTO getPromotionById(int id);
    List<CouponDTO> getAllPromotion();
    boolean updatePromotion(int id, String token);

}

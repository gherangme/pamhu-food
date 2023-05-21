package com.example.securityhibernate.mapper;

import com.example.securityhibernate.dto.CouponDTO;
import com.example.securityhibernate.entity.Coupon;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

    public CouponDTO convertEntityToDTO(Coupon coupon) {
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setId(coupon.getId());
        couponDTO.setName(coupon.getName());
        couponDTO.setVoucher(coupon.getVoucher());

        return couponDTO;
    }

    public Coupon convertDTOToEntity(CouponDTO couponDTO) {
        Coupon coupon = new Coupon();
        coupon.setId(couponDTO.getId());
        coupon.setName(couponDTO.getName());
        coupon.setVoucher((float) couponDTO.getVoucher());

        return coupon;
    }

}
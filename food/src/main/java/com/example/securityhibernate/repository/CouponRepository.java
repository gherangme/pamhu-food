package com.example.securityhibernate.repository;

import com.example.securityhibernate.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    Coupon findById(int id);
    Coupon findByName(String name);

}

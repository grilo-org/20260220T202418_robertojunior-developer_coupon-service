package com.teste.coupon_service.application.ports;

import com.teste.coupon_service.domain.coupon.Coupon;
import com.teste.coupon_service.domain.coupon.CouponCode;

import java.util.Optional;

public interface CouponRepository {
    Coupon save(Coupon coupon);
    Optional<Coupon> findByCode(CouponCode code);
    boolean existsByCode(CouponCode code);
}

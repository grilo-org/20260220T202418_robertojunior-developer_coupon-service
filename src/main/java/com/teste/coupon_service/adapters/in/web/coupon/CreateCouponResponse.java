package com.teste.coupon_service.adapters.in.web.coupon;

import com.teste.coupon_service.application.coupon.create.CreateCouponHandler;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateCouponResponse(String id,
                                   String code,
                                   String description,
                                   BigDecimal discountValue,
                                   LocalDate expirationDate,
                                   boolean published) {
    public static CreateCouponResponse from(CreateCouponHandler.Output o) {
        return new CreateCouponResponse(o.id(), o.code(), o.description(), o.discountValue(), o.expirationDate(), o.published());
    }
}

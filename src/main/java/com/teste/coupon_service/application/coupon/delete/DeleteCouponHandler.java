package com.teste.coupon_service.application.coupon.delete;

import com.teste.coupon_service.application.ports.CouponRepository;
import com.teste.coupon_service.domain.AlreadyDeleted;
import com.teste.coupon_service.domain.EntityNotFound;
import com.teste.coupon_service.domain.coupon.Coupon;
import com.teste.coupon_service.domain.coupon.CouponCode;

public class DeleteCouponHandler {

    private final CouponRepository repository;

    public DeleteCouponHandler(CouponRepository repository) {
        this.repository = repository;
    }

    public void execute(String rawCode) {
        CouponCode code = CouponCode.ofRaw(rawCode);
        Coupon coupon = repository.findByCode(code)
                .orElseThrow(() -> new EntityNotFound("Coupon not found"));
        coupon.delete();
        repository.save(coupon);
    }
}

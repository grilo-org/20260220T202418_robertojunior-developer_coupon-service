package com.teste.coupon_service.application.coupon.create;

import com.teste.coupon_service.application.ports.CouponRepository;
import com.teste.coupon_service.domain.BusinessRuleViolation;
import com.teste.coupon_service.domain.coupon.Coupon;
import com.teste.coupon_service.domain.coupon.CouponCode;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateCouponHandler {

    private final CouponRepository repository;

    public CreateCouponHandler(CouponRepository repository) {
        this.repository = repository;
    }

    public Output execute(Input input) {
        if (input == null) throw new IllegalArgumentException("input is required");
        CouponCode code = CouponCode.ofRaw(input.code());
        if (repository.existsByCode(code)) {
            throw new BusinessRuleViolation("Coupon code already exists");
        }
        Coupon coupon = Coupon.create(
                code,
                input.description(),
                input.discountValue(),
                input.expirationDate(),
                input.published() != null && input.published()
        );
        Coupon saved = repository.save(coupon);
        return new Output(
                saved.getId().toString(),
                saved.getCode().getValue(),
                saved.getDescription(),
                saved.getDiscountValue(),
                saved.getExpirationDate(),
                saved.isPublished()
        );
    }

    public record Input(String code,
                        String description,
                        BigDecimal discountValue,
                        LocalDate expirationDate,
                        Boolean published) {}

    public record Output(String id,
                         String code,
                         String description,
                         BigDecimal discountValue,
                         LocalDate expirationDate,
                         boolean published) {}
}

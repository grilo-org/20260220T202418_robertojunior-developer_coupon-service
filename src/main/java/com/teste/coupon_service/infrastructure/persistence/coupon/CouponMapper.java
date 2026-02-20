package com.teste.coupon_service.infrastructure.persistence.coupon;

import com.teste.coupon_service.domain.coupon.Coupon;
import com.teste.coupon_service.domain.coupon.CouponCode;

public final class CouponMapper {
    private CouponMapper() {}

    public static CouponEntity toEntity(Coupon domain) {
        CouponEntity e = new CouponEntity();
        e.setId(domain.getId());
        e.setCode(domain.getCode().getValue());
        e.setDescription(domain.getDescription());
        e.setDiscountValue(domain.getDiscountValue());
        e.setExpirationDate(domain.getExpirationDate());
        e.setPublished(domain.isPublished());
        e.setCreatedAt(domain.getCreatedAt());
        e.setUpdatedAt(domain.getUpdatedAt());
        e.setDeletedAt(domain.getDeletedAt());
        return e;
    }

    public static Coupon toDomain(CouponEntity e) {
        return Coupon.rehydrate(
                e.getId(),
                CouponCode.ofRaw(e.getCode()),
                e.getDescription(),
                e.getDiscountValue(),
                e.getExpirationDate(),
                e.isPublished(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getDeletedAt()
        );
    }
}

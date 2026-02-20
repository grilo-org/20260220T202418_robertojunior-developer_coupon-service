package com.teste.coupon_service.infrastructure.persistence.coupon;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataCouponJpaRepository extends JpaRepository<CouponEntity, UUID> {
    Optional<CouponEntity> findByCode(String code);
    boolean existsByCode(String code);
}

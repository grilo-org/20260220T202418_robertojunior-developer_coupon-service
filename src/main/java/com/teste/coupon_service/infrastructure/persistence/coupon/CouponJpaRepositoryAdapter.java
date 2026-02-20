package com.teste.coupon_service.infrastructure.persistence.coupon;

import com.teste.coupon_service.application.ports.CouponRepository;
import com.teste.coupon_service.domain.coupon.Coupon;
import com.teste.coupon_service.domain.coupon.CouponCode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public class CouponJpaRepositoryAdapter implements CouponRepository {

    private final SpringDataCouponJpaRepository jpa;

    public CouponJpaRepositoryAdapter(SpringDataCouponJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Coupon save(Coupon coupon) {
        CouponEntity entity = CouponMapper.toEntity(coupon);
        CouponEntity saved = jpa.save(entity);
        return CouponMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Coupon> findByCode(CouponCode code) {
        return jpa.findByCode(code.getValue()).map(CouponMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCode(CouponCode code) {
        return jpa.existsByCode(code.getValue());
    }
}

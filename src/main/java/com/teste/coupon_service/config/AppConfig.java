package com.teste.coupon_service.config;

import com.teste.coupon_service.application.coupon.create.CreateCouponHandler;
import com.teste.coupon_service.application.coupon.delete.DeleteCouponHandler;
import com.teste.coupon_service.application.ports.CouponRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CreateCouponHandler createCouponHandler(CouponRepository repo) {
        return new CreateCouponHandler(repo);
    }

    @Bean
    public DeleteCouponHandler deleteCouponHandler(CouponRepository repo) {
        return new DeleteCouponHandler(repo);
    }
}

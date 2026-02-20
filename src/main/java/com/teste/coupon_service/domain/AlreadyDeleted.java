package com.teste.coupon_service.domain;

public class AlreadyDeleted extends RuntimeException {
    public AlreadyDeleted(String message) {
        super(message);
    }
}

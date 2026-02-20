package com.teste.coupon_service.domain.coupon;

import java.util.Locale;
import java.util.Objects;

public final class CouponCode {
    private final String value;

    private CouponCode(String value) {
        this.value = value;
    }

    public static CouponCode ofRaw(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("code is required");
        }
        String normalized = normalize(raw);
        if (normalized.length() != 6) {
            throw new IllegalArgumentException("code must have exactly 6 alphanumeric characters after normalization");
        }
        return new CouponCode(normalized);
    }

    public static String normalize(String raw) {
        String onlyAlnum = raw.replaceAll("[^A-Za-z0-9]", "");
        return onlyAlnum.toUpperCase(Locale.ROOT);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CouponCode that = (CouponCode) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

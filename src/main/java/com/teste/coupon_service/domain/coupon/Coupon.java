package com.teste.coupon_service.domain.coupon;

import com.teste.coupon_service.domain.BusinessRuleViolation;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Coupon {
    private final UUID id;
    private final CouponCode code;
    private final String description;
    private final BigDecimal discountValue;
    private final LocalDate expirationDate;
    private final boolean published;
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Coupon(UUID id,
                   CouponCode code,
                   String description,
                   BigDecimal discountValue,
                   LocalDate expirationDate,
                   boolean published,
                   Instant createdAt,
                   Instant updatedAt,
                   Instant deletedAt) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.published = published;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Coupon create(CouponCode code,
                                String description,
                                BigDecimal discountValue,
                                LocalDate expirationDate,
                                boolean published) {
        if (code == null) throw new IllegalArgumentException("code is required");
        if (description == null || description.isBlank()) throw new IllegalArgumentException("description is required");
        if (discountValue == null) throw new IllegalArgumentException("discountValue is required");
        if (discountValue.compareTo(new BigDecimal("0.5")) < 0) {
            throw new BusinessRuleViolation("discountValue must be >= 0.5");
        }
        if (expirationDate == null) throw new IllegalArgumentException("expirationDate is required");
        LocalDate today = LocalDate.now();
        if (expirationDate.isBefore(today)) {
            throw new BusinessRuleViolation("expirationDate cannot be in the past");
        }
        Instant now = Instant.now();
        return new Coupon(
                UUID.randomUUID(),
                code,
                description,
                discountValue,
                expirationDate,
                published,
                now,
                now,
                null
        );
    }

    public static Coupon rehydrate(UUID id,
                                   CouponCode code,
                                   String description,
                                   BigDecimal discountValue,
                                   LocalDate expirationDate,
                                   boolean published,
                                   Instant createdAt,
                                   Instant updatedAt,
                                   Instant deletedAt) {
        return new Coupon(id, code, description, discountValue, expirationDate, published, createdAt, updatedAt, deletedAt);
    }

    public void delete() {
        if (this.deletedAt != null) {
            throw new com.teste.coupon_service.domain.AlreadyDeleted("Coupon already deleted");
        }
        this.deletedAt = Instant.now();
        this.updatedAt = this.deletedAt;
    }

    public UUID getId() { return id; }
    public CouponCode getCode() { return code; }
    public String getDescription() { return description; }
    public BigDecimal getDiscountValue() { return discountValue; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public boolean isPublished() { return published; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Instant getDeletedAt() { return deletedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return Objects.equals(id, coupon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

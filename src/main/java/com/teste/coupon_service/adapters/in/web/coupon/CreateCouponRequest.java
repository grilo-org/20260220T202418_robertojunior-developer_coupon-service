package com.teste.coupon_service.adapters.in.web.coupon;

import com.teste.coupon_service.application.coupon.create.CreateCouponHandler;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateCouponRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String description;

    @NotNull
    @DecimalMin(value = "0.5")
    private BigDecimal discountValue;

    @NotNull
    private LocalDate expirationDate;

    private Boolean published;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getDiscountValue() { return discountValue; }
    public void setDiscountValue(BigDecimal discountValue) { this.discountValue = discountValue; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    public Boolean getPublished() { return published; }
    public void setPublished(Boolean published) { this.published = published; }

    public CreateCouponHandler.Input toInput() {
        return new CreateCouponHandler.Input(code, description, discountValue, expirationDate, published);
    }
}

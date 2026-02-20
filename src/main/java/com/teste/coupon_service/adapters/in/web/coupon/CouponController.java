package com.teste.coupon_service.adapters.in.web.coupon;

import com.teste.coupon_service.application.coupon.create.CreateCouponHandler;
import com.teste.coupon_service.application.coupon.delete.DeleteCouponHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupons")
public class CouponController {

    private final CreateCouponHandler createCouponHandler;
    private final DeleteCouponHandler deleteCouponHandler;

    public CouponController(CreateCouponHandler createCouponHandler,
                            DeleteCouponHandler deleteCouponHandler) {
        this.createCouponHandler = createCouponHandler;
        this.deleteCouponHandler = deleteCouponHandler;
    }

    @PostMapping
    public ResponseEntity<CreateCouponResponse> create(@Valid @RequestBody CreateCouponRequest request) {
        var output = createCouponHandler.execute(request.toInput());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CreateCouponResponse.from(output));
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        deleteCouponHandler.execute(code);
    }
}

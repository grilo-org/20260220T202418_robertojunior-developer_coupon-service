package com.teste.coupon_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.coupon_service.infrastructure.persistence.coupon.SpringDataCouponJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CouponApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    SpringDataCouponJpaRepository jpaRepository;

    @BeforeEach
    void setup() {
        jpaRepository.deleteAll();
    }

    @Test
    void create_valid_returns_201_and_normalizes_code() throws Exception {
        var body = Map.of(
                "code", "ab-12#c9",
                "description", "10% OFF",
                "discountValue", 10.0,
                "expirationDate", LocalDate.now().plusDays(10).toString(),
                "published", true
        );

        var mvcResult = mockMvc.perform(post("/api/v1/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("AB12C9"))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertThat(response).contains("\"code\":\"AB12C9\"");
    }

    @Test
    void create_code_not_6_after_normalization_returns_400() throws Exception {
        var body = Map.of(
                "code", "a-b1",
                "description", "10% OFF",
                "discountValue", 10.0,
                "expirationDate", LocalDate.now().plusDays(10).toString()
        );

        mockMvc.perform(post("/api/v1/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_discount_too_low_returns_400() throws Exception {
        var body = Map.of(
                "code", "ab-12#c9",
                "description", "10% OFF",
                "discountValue", 0.4,
                "expirationDate", LocalDate.now().plusDays(10).toString()
        );

        mockMvc.perform(post("/api/v1/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_expiration_in_past_returns_400() throws Exception {
        var body = Map.of(
                "code", "ab-12#c9",
                "description", "10% OFF",
                "discountValue", 10.0,
                "expirationDate", LocalDate.now().minusDays(1).toString()
        );

        mockMvc.perform(post("/api/v1/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_duplicate_code_returns_400() throws Exception {
        var body = Map.of(
                "code", "ab-12#c9",
                "description", "10% OFF",
                "discountValue", 10.0,
                "expirationDate", LocalDate.now().plusDays(10).toString()
        );

        mockMvc.perform(post("/api/v1/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_valid_returns_204_and_sets_deletedAt() throws Exception {
        var body = Map.of(
                "code", "ab-12#c9",
                "description", "10% OFF",
                "discountValue", 10.0,
                "expirationDate", LocalDate.now().plusDays(10).toString()
        );
        mockMvc.perform(post("/api/v1/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/api/v1/coupons/{code}", "ab-12#c9"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_nonexistent_returns_404() throws Exception {
        mockMvc.perform(delete("/api/v1/coupons/{code}", "xx-00#zz"))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_twice_returns_409_on_second() throws Exception {
        var body = Map.of(
                "code", "ab-12#c9",
                "description", "10% OFF",
                "discountValue", 10.0,
                "expirationDate", LocalDate.now().plusDays(10).toString()
        );
        mockMvc.perform(post("/api/v1/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/api/v1/coupons/{code}", "ab-12#c9"))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/api/v1/coupons/{code}", "ab-12#c9"))
                .andExpect(status().isConflict());
    }
}

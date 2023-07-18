package com.nitzan.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nitzan.entities.Coupon;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class CustomerDto {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Coupon> coupons;
}

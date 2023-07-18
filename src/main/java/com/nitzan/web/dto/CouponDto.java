package com.nitzan.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode(of = "uuid")
public class CouponDto {
    private UUID uuid;
    private String title;
    private String description;
    private Timestamp startDate;
    private Timestamp endDate;
    private String imageUrl;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal price;

    private int amount;
}

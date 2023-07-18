package com.nitzan.mapper;

import com.nitzan.entities.Coupon;
import com.nitzan.web.dto.CouponDto;
import org.mapstruct.Mapper;

@Mapper
public interface CouponMapper {
    Coupon map(CouponDto couponDto);

    CouponDto map(Coupon coupon);
}

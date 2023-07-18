package com.nitzan.repository;

import com.nitzan.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

}

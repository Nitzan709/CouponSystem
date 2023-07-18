package com.nitzan.repository;

import com.nitzan.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailAndPassword(String email, String password);

    Optional<Customer> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    @Modifying
    @Query("update Customer set email = :email where uuid = :uuid")
    void updateEmailByUuid(String email, UUID uuid);

    @Modifying
    @Query("update Customer set password = :password where uuid = :uuid")
    void updatePasswordByUuid(String password, UUID uuid);
}

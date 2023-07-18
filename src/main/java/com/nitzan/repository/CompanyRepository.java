package com.nitzan.repository;

import com.nitzan.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByEmailAndPassword(String email, String password);

    Optional<Company> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    @Modifying
    @Query("update Company set email = :email where uuid = :uuid")
    void updateEmailByUuid(String email, UUID uuid);

    @Modifying
    @Query("update Company set password = :password where uuid = :uuid")
    void updatePasswordByUuid(String password, UUID uuid);
}

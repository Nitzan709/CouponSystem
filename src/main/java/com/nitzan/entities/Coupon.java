package com.nitzan.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = 36, updatable = false, unique = true)
    private UUID uuid;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private int category;
    @Column(nullable = false)
    private Timestamp startDate;
    @Column(nullable = false)
    private Timestamp endDate;
    private String description;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String imageUrl;
    @ManyToMany
    @JoinTable(name = "customer_coupon",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Set<Customer> customers;
}

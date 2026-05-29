package com.EcomFashion.Ecommorse.Entity.Paymnet;


import com.EcomFashion.Ecommorse.Entity.Order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String transactionId;

    private BigDecimal amount;

    private String status;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

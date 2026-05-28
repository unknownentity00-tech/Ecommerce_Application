package com.EcomFashion.Ecommorse.Repositories;

import com.EcomFashion.Ecommorse.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository
        extends JpaRepository<Payment, Long>{
}

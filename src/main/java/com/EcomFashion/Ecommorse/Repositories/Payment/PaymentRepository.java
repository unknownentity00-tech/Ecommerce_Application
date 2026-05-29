package com.EcomFashion.Ecommorse.Repositories.Payment;

import com.EcomFashion.Ecommorse.Entity.Paymnet.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository
        extends JpaRepository<Payment, Long>{
}

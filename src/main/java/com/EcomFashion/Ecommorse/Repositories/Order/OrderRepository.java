package com.EcomFashion.Ecommorse.Repositories.Order;

import com.EcomFashion.Ecommorse.Entity.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository
        extends JpaRepository<Order, Long> {
}

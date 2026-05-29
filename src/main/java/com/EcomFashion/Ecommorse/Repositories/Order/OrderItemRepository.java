package com.EcomFashion.Ecommorse.Repositories.Order;

import com.EcomFashion.Ecommorse.Entity.Order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {
}

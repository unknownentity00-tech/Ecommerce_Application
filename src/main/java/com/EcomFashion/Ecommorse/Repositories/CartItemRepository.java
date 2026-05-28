package com.EcomFashion.Ecommorse.Repositories;

import com.EcomFashion.Ecommorse.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository
        extends JpaRepository<CartItem, Long> {
}

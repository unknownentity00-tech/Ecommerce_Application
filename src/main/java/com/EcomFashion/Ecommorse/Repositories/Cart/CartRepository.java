package com.EcomFashion.Ecommorse.Repositories.Cart;

import com.EcomFashion.Ecommorse.Entity.Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository
        extends JpaRepository<Cart, Long> {
}

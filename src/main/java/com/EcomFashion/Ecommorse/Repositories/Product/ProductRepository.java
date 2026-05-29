package com.EcomFashion.Ecommorse.Repositories.Product;

import com.EcomFashion.Ecommorse.Entity.Product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findByCategory_NameIgnoreCase(String category, Pageable pageable);

    Page<Product> findByCategory_NameIgnoreCaseAndNameContainingIgnoreCase(String category, String keyword, Pageable pageable);
}

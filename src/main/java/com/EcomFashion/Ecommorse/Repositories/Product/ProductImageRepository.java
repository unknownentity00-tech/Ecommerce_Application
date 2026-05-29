package com.EcomFashion.Ecommorse.Repositories.Product;

import com.EcomFashion.Ecommorse.Entity.Product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository   extends JpaRepository<ProductImage, Long> {
}

package com.EcomFashion.Ecommorse.Entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionCreationRequest {


    private String name;

    private String description;

    private String brand;

    private Long categoryId;

    private List<ProductVariantRequestDTO> variants;

    private List<String> imageUrls;

    private BigDecimal basePrice;
}

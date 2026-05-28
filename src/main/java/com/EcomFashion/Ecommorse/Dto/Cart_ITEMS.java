package com.EcomFashion.Ecommorse.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart_ITEMS {

    private Long id;

    private Long cartId;

    private Long productVariantId;

    private Integer quantity;
}

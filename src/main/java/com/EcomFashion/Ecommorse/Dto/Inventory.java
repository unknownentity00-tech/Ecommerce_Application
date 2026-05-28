package com.EcomFashion.Ecommorse.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    private Long id;

    private Long productVariantId;

    private Integer quantityChanged;

    private String eventType;
}

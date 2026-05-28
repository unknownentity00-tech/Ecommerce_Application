package com.EcomFashion.Ecommorse.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class reviews {

    private Long id;

    private Long userId;

    private Long productId;

    private Integer rating;

    private String reviewText;
}
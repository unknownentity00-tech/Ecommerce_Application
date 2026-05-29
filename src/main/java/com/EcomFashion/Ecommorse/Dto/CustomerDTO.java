package com.EcomFashion.Ecommorse.Dto;

import com.EcomFashion.Ecommorse.Entity.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;

    private String username;

    private String email;

    private Role role;
}

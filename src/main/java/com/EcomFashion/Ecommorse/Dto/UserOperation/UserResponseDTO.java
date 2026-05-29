package com.EcomFashion.Ecommorse.Dto.UserOperation;

import com.EcomFashion.Ecommorse.Entity.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private Role role;
}

package com.EcomFashion.Ecommorse.Dto.UserOperation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDTO {
    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String email;
    @Size(min = 6)
    private String password;
    private String captchaToken;
}

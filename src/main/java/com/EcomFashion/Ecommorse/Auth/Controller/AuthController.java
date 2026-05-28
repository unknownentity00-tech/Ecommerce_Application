package com.EcomFashion.Ecommorse.Auth.Controller;


import com.EcomFashion.Ecommorse.Auth.Dto.AuthResponseDTO;
import com.EcomFashion.Ecommorse.Auth.Service.AuthService;
import com.EcomFashion.Ecommorse.Dto.UserOperation.UserLoginRequestDTO;
import com.EcomFashion.Ecommorse.Dto.UserOperation.UserRegisterRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @GetMapping("/me")
    public String currentUser(Authentication authentication) {
        return authentication.getName();
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody UserRegisterRequestDTO request )
    { AuthResponseDTO response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody UserLoginRequestDTO request )
    { AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response); }



}




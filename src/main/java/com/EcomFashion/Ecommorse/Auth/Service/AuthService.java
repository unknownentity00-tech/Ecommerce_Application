package com.EcomFashion.Ecommorse.Auth.Service;

import com.EcomFashion.Ecommorse.Auth.Dto.AuthResponseDTO;
import com.EcomFashion.Ecommorse.Dto.UserOperation.UserLoginRequestDTO;
import com.EcomFashion.Ecommorse.Dto.UserOperation.UserRegisterRequestDTO;
import com.EcomFashion.Ecommorse.Entity.User;
import com.EcomFashion.Ecommorse.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CaptchaService captchaService;
    private final AuthenticationManager authenticationManager;

    private AuthResponseDTO buildAuthResponse(User user, String accessToken, String refreshToken
    ) {
        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .expiresIn(86400000L)
                .build();
    }
    public AuthResponseDTO register(
            UserRegisterRequestDTO request
    ) {
//        if (!captchaService.verifyCaptcha(
//                request.getCaptchaToken()
//        )) {
//
//            throw new RuntimeException(
//                    "Invalid captcha"
//            );
//        }
        if (userRepository.existsByEmail(
                request.getEmail()
        )) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }
      User user = new User();
        user.setUsername(
                request.getUsername()
        );
        user.setEmail(
                request.getEmail()
        );
        user.setPasswordHash(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );
        user.setRole("CUSTOMER");
        userRepository.save(user);
         String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return buildAuthResponse( user, accessToken, refreshToken );
    }
    public AuthResponseDTO login(
            UserLoginRequestDTO request
    ) {  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user =
                userRepository.findByEmail(
                        request.getEmail()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );
        String accessToken =
                jwtService.generateAccessToken(user);

        String refreshToken =
                jwtService.generateRefreshToken(user);
        return buildAuthResponse( user, accessToken, refreshToken );
    }
}




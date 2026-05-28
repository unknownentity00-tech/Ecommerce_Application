package com.EcomFashion.Ecommorse.Auth.Service;
import com.EcomFashion.Ecommorse.Entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class JwtService {



        @Value("${jwt.secret-key}")
        private String secretKey;

       @Value("${jwt.access-token-expiration}")
        private long accessTokenExpiration;

       @Value("${jwt.refresh-token-expiration}")
       private long refreshTokenExpiration;

       @Value("${jwt.issuer}")
        private String issuer;

       private SecretKey key;


      @PostConstruct
      public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
      }

    public String generateAccessToken(User user) {
        return buildToken(
                user,
                accessTokenExpiration,
                "ACCESS"
        );
    }



    public String generateRefreshToken(User user) {
        return buildToken(
                user,
                refreshTokenExpiration,
                "REFRESH"
        );
    }

    private String buildToken(
            User user,
            long expiration,
            String tokenType
    ) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuer(issuer)
                .setAudience("ecommerce-app")
                .setIssuedAt(Date.from(now))
                .setExpiration(
                        Date.from(now.plusMillis(expiration)))
                .addClaims(Map.of(
                        "email", user.getEmail(),
                        "role", user.getRole(),
                        "tokenType", tokenType
                ))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            getAllClaims(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("JWT expired: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Malformed JWT: {}", ex.getMessage());
        } catch (SecurityException ex) {
            log.error("Invalid signature: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string empty: {}", ex.getMessage());
        }
        return false;
    }

    public Long extractUserId(String token) {
        return extractClaim(
                token,
                claims -> Long.valueOf(claims.getSubject())
        );
    }


    public String extractEmail(String token) {
        return extractClaim(
                token,
                claims -> claims.get("email", String.class)
        );
    }


    public String extractRole(String token) {
        return extractClaim(
                token,
                claims -> claims.get("role", String.class)
        );
    }

    public String extractTokenType(String token) {

        return extractClaim(
                token,
                claims -> claims.get("tokenType", String.class)
        );
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver
    ) {

        Claims claims = getAllClaims(token);

        return resolver.apply(claims);
    }
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .requireIssuer(issuer)
                .setAllowedClockSkewSeconds(30)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean isTokenExpired(String token) {

        Date expiration = extractClaim(
                token,
                Claims::getExpiration
        );

        return expiration.before(new Date());
    }

    public void validateAccessToken(String token) {
        if (!isTokenValid(token)) {
            throw new BadCredentialsException(
                    "Invalid access token"
            );
        }
        String tokenType = extractTokenType(token);
        if (!"ACCESS".equals(tokenType)) {
            throw new BadCredentialsException(
                    "Invalid token type"
            );
        }

    }}

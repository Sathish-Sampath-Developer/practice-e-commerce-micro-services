package com.eshop.authservice.service.impl;

import com.eshop.authservice.exception.ServiceException;
import com.eshop.authservice.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtServiceImpl implements JwtService {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expires-in}")
    private long jwtExpiresIn;

    // generate JWT token
    public String generateToken(Authentication authentication) {
        String phoneOrEmail = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpiresIn);

        return Jwts.builder()
                .subject(phoneOrEmail)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key())
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    // get phoneOrEmail from Jwt token
    public String getPhoneOrEmail(String token) {
        Claims claims = Jwts.parser().setSigningKey(key()).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    // validate Jwt token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key()).build().parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }
}

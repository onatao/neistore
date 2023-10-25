package com.neidev.store.security.infra.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.neidev.store.domain.core.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtTokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("neistore")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateTokenCycle())
                    .sign(algorithm);
        } catch(JWTCreationException e) {
            throw new RuntimeException("ERROR: There's a error while trying to generate a new token");
        }
    }

    public String verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .build().verify(token)
                    .getSubject();
        } catch(JWTVerificationException e) {
            throw new RuntimeException("ERROR: There's a error while verifying token");
        }
    }

    Instant generateTokenCycle() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(
                        ZoneOffset.of("-03")
                );
    }

}

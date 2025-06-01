package com.deloitte.bootcamp.api_backend.security;

import com.deloitte.bootcamp.api_backend.model.entity.User;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JwtUtil {

    @Value("${secret_key}")
    private String SECRET;

    private SecretKey SECRET_KEY;

    // Inicializa a chave secreta após a construção do bean
    @PostConstruct
    public void init() {
        SECRET_KEY = io.jsonwebtoken.security.Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Gera um token JWT com o assunto fornecido
    public String generateToken(User user) {

        Map<String, String> claims = new HashMap<String, String>();
        claims.put("roles", String.valueOf(user.getRoleName()));
        claims.put("email", user.getEmail());

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 100 * 60 * 60)) // 1 hora
                .signWith(SECRET_KEY)
                .claims(claims)
                .compact();
    }
}

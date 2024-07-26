package com.base.Spring.Security.service.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    // Variables del JWT
    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    // Metodo para generar el token
    public String generateToken(UserDetails user,
                                Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date( (EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime());

        // Configuramos el JWT cada una de sus partes
        String jwt = Jwts.builder()
                // Header del JWT
                .header()
                .type("JWT")
                .and()

                // Payload del JWT
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(extraClaims)

                // Firma del JWT
                .signWith(generateKey(), Jwts.SIG.HS256)
                .compact();
        return jwt;
    }

    // Se encarga de generar una clave criptografica que se utiliza en operaciones
    // HMAC (Codigo de autorización de mensajes con Hash), particularmente para el
    // manejo de JWT
    private SecretKey generateKey(){
        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }
}



package com.project.shopping.service;
import com.project.shopping.Repositories.Customers_db;
import com.project.shopping.model.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class JwtAuthentication {
    private static JwtAuthentication instance;

    public static JwtAuthentication getInstance() {
        if (instance == null) {
            synchronized (Customers_db.class) {
                if (instance == null) {
                    instance = new JwtAuthentication();
                }
            }
        }
        return instance;
    }

    byte[] keyBytes = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512).getEncoded();

    String base64EncodedKey = java.util.Base64.getEncoder().encodeToString(keyBytes);
    private final String SECRET_KEY = base64EncodedKey;

    // Generate a token from the user's authentication details
    public String generateToken(Customer c) {
        return Jwts.builder()
                .setSubject(String.valueOf(c.getID()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // Validate a token by checking its signature and expiration date
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Token is invalid (expired, tampered with, etc.)
            return false;
        }
    }

    // Extract the username from a token
    public String getIDFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}

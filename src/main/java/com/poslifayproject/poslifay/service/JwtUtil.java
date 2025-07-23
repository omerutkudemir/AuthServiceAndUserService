package com.poslifayproject.poslifay.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final JwtKeyProvider keyProvider;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 saat

    public JwtUtil(JwtKeyProvider keyProvider) {
        this.keyProvider = keyProvider;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        // Token'ı doğrulamak için Public Key kullanılır
        return Jwts.parserBuilder()
                .setSigningKey(keyProvider.getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Token'a yetkileri (authorities) de ekleyebiliriz.
        // claims.put("authorities", userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String username) {
        // Token'ı oluştururken Private Key ile imzalarız
        return Jwts.builder()
                .setClaims(claims)
                .setHeaderParam("kid", keyProvider.getKeyId()) // Header'a Key ID ekliyoruz
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(keyProvider.getPrivateKey(), SignatureAlgorithm.RS256) // Algoritmayı RS256 olarak değiştiriyoruz
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

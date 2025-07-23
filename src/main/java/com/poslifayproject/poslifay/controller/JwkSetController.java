package com.poslifayproject.poslifay.controller;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.poslifayproject.poslifay.service.JwtKeyProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@RestController
public class JwkSetController {

    private final JwtKeyProvider keyProvider;

    public JwkSetController(JwtKeyProvider keyProvider) {
        this.keyProvider = keyProvider;
    }

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> jwks() {
        RSAPublicKey publicKey = keyProvider.getPublicKey();

        // Nimbus JOSE+JWT kütüphanesini kullanarak JWK oluşturuyoruz.
        // Bu kütüphaneyi pom.xml'e eklemeniz gerekecek.
        JWK jwk = new RSAKey.Builder(publicKey)
                .keyID(keyProvider.getKeyId())
                .build();

        JWKSet jwkSet = new JWKSet(jwk);

        return jwkSet.toJSONObject();
    }
}

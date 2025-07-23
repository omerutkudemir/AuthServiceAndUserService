package com.poslifayproject.poslifay.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

/**
 * Bu sınıf, uygulama başlangıcında RSA Private ve Public anahtarlarını
 * `resources/keys` klasöründeki .pem dosyalarından yükler.
 * Anahtarlar, JWT oluşturma ve doğrulama işlemleri için kullanılır.
 * Ayrıca JWKS endpoint'i için bir Key ID (kid) oluşturur.
 */
@Component
public class JwtKeyProvider {

    private PrivateKey privateKey;
    private RSAPublicKey publicKey;
    private String keyId;

    /**
     * Bu metot, Spring bean'i oluşturulduktan hemen sonra çalışır (@PostConstruct).
     * RSA anahtar çiftini dosyadan okur, temizler ve PrivateKey/PublicKey nesnelerine dönüştürür.
     */
    @PostConstruct
    public void init() {
        try {
            // JWKS'te bu anahtarı ayırt etmek için kullanılacak benzersiz bir kimlik oluşturulur.
            this.keyId = UUID.randomUUID().toString();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            // --- Private Key'i Yükleme ---
            InputStream privateKeyStream = getClass().getResourceAsStream("/keys/private_key.pem");
            if (privateKeyStream == null) {
                throw new IllegalStateException("Private key dosyası bulunamadı: /keys/private_key.pem. Dosyanın doğru yolda olduğundan emin olun.");
            }
            byte[] privateKeyBytes = privateKeyStream.readAllBytes();

            // .pem dosyasının içeriğindeki başlık, altlık ve tüm boşluk karakterleri temizlenir.
            // Bu, 'Illegal base64 character' hatasını önlemek için kritik bir adımdır.
            String privateKeyPEM = new String(privateKeyBytes, StandardCharsets.UTF_8)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", ""); // Bütün whitespace karakterlerini (newline, space, tab vb.) kaldırır.

            byte[] decodedPrivateKey = Base64.getDecoder().decode(privateKeyPEM);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodedPrivateKey);
            this.privateKey = keyFactory.generatePrivate(privateKeySpec);


            // --- Public Key'i Yükleme ---
            InputStream publicKeyStream = getClass().getResourceAsStream("/keys/public_key.pem");
            if (publicKeyStream == null) {
                throw new IllegalStateException("Public key dosyası bulunamadı: /keys/public_key.pem. Dosyanın doğru yolda olduğundan emin olun.");
            }
            byte[] publicKeyBytes = publicKeyStream.readAllBytes();

            // .pem dosyasının içeriğindeki başlık, altlık ve tüm boşluk karakterleri temizlenir.
            String publicKeyPEM = new String(publicKeyBytes, StandardCharsets.UTF_8)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", ""); // Bütün whitespace karakterlerini (newline, space, tab vb.) kaldırır.

            byte[] decodedPublicKey = Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodedPublicKey);
            this.publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

        } catch (Exception e) {
            // Hata durumunda uygulamanın başlamasını engelleyerek sorunun fark edilmesini sağlar.
            // Konsoldaki hata detayları sorunun kaynağını bulmak için incelenmelidir.
            throw new IllegalStateException("RSA anahtar çifti yüklenirken kritik bir hata oluştu. Lütfen .pem dosyalarının formatını ve yolunu kontrol edin.", e);
        }
    }

    /**
     * JWT imzalamak için kullanılacak PrivateKey nesnesini döndürür.
     * @return PrivateKey
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * JWT doğrulamak ve JWKS endpoint'inde yayınlamak için kullanılacak PublicKey nesnesini döndürür.
     * @return RSAPublicKey
     */
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * JWKS setinde bu anahtar çiftini tanımlayan benzersiz kimliği (Key ID) döndürür.
     * @return String Key ID
     */
    public String getKeyId() {
        return keyId;
    }
}

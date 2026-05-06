package com.dht.utils;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.util.Date;


public class JwtUtils {
    // Secret key tối thiểu 32 ký tự
    private static final String SECRET = "12345678901234567890123456789012"; 
    private static final long EXPIRATION_MS = 86400000; // 1 ngày

    // THAY ĐỔI: Nhận thêm tham số role
    public static String generateToken(String username, String role) throws Exception {
        JWSSigner signer = new MACSigner(SECRET);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .claim("role", role) // Quan trọng: Đưa role vào token
                .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .issueTime(new Date())
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                claimsSet
        );

        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    // Hàm bổ trợ để lấy toàn bộ Claims (tránh lặp code)
    public static JWTClaimsSet getClaims(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SECRET);

        if (signedJWT.verify(verifier)) {
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            if (claims.getExpirationTime().after(new Date())) {
                return claims;
            }
        }
        return null;
    }

    public static String validateTokenAndGetUsername(String token) throws Exception {
        JWTClaimsSet claims = getClaims(token);
        return (claims != null) ? claims.getSubject() : null;
    }

    // THÊM: Lấy role để JwtFilter nạp vào SecurityContext
    public static String getRoleFromToken(String token) throws Exception {
        JWTClaimsSet claims = getClaims(token);
        return (claims != null) ? claims.getStringClaim("role") : null;
    }
}
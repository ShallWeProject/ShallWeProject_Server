package com.shallwe.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shallwe.domain.auth.dto.request.AppleTokenReq;
import com.shallwe.domain.auth.dto.request.AppleTokenRevokeReq;
import com.shallwe.global.config.security.AuthConfig;
import com.shallwe.domain.auth.dto.response.ApplePublicKeyRes;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import static com.shallwe.global.config.security.AuthConfig.*;

@Component
@RequiredArgsConstructor
public class AppleJwtUtils {

    private final AuthConfig authConfig;

    public Claims getClaimsBy(String identityToken) {
        try {
            RestClient restClient = RestClient.builder()
                    .requestFactory(new HttpComponentsClientHttpRequestFactory())
                    .baseUrl("https://appleid.apple.com/auth")
                    .build();

            ApplePublicKeyRes response = restClient.get()
                    .uri("/keys")
                    .retrieve()
                    .body(ApplePublicKeyRes.class);


            String headerOfIdentityToken = identityToken.substring(0, identityToken.indexOf("."));
            Map<String, String> header = new ObjectMapper().readValue(new String(Base64.getDecoder().decode(headerOfIdentityToken), "UTF-8"), Map.class);
            ApplePublicKeyRes.Key key = response.getMatchedKeyBy(header.get("kid"), header.get("alg")).orElseThrow(() -> new IllegalArgumentException("Not found matched key"));

            byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
            byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

            BigInteger n = new BigInteger(1, nBytes);
            BigInteger e = new BigInteger(1, eBytes);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(identityToken).getBody();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Not found algorithm");
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException("Invalid key spec");
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Invalid signature");
        } catch (MalformedJwtException e) {
            throw new IllegalArgumentException("Malformed jwt");
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Expired jwt");
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid jwt");
        }
    }

    public String getAppleToken(String code) {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://appleid.apple.com/auth")
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .build();

        AppleTokenReq.Request request = AppleTokenReq.Request.of(
                code,
                authConfig.getAppleAuth().getClientId(),
                makeClientSecret(),
                "authorization_code",
                null
        );

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", request.getClient_id());
        map.add("client_secret", request.getClient_secret());
        map.add("code", request.getCode());
        map.add("grant_type", request.getGrant_type());
        map.add("refresh_token", request.getRefresh_token());

        AppleTokenReq.Response response = restClient.post()
                .uri("/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .body(AppleTokenReq.Response.class);

        return response.getRefresh_token();
    }

    public void revokeToken(String refreshToken) {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://appleid.apple.com/auth")
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .build();

        AppleTokenRevokeReq appleTokenRevokeReq = AppleTokenRevokeReq.of(
                authConfig.getAppleAuth().getClientId(),
                makeClientSecret(),
                refreshToken
        );

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", appleTokenRevokeReq.getClient_id());
        map.add("client_secret", appleTokenRevokeReq.getClient_secret());
        map.add("token", appleTokenRevokeReq.getToken());

        restClient.post()
                .uri("/revoke")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .toBodilessEntity();
    }

    public String makeClientSecret() {
        AppleAuth appleAuth = authConfig.getAppleAuth();
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setHeaderParam("kid", appleAuth.getKeyId())
                .setHeaderParam("alg", "ES256")
                .setIssuer(appleAuth.getTeamId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .setAudience("https://appleid.apple.com")
                .setSubject(appleAuth.getClientId())
                .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    private PrivateKey getPrivateKey() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(authConfig.getAppleAuth().getPrivateKey());
            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(privateKeyBytes);
            return converter.getPrivateKey(privateKeyInfo);
        } catch (Exception e) {
            throw new RuntimeException("Error converting private key from String", e);
        }
    }

}

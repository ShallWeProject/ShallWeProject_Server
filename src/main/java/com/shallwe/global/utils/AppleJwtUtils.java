package com.shallwe.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shallwe.global.infrastructure.feign.apple.AppleClient;
import com.shallwe.global.infrastructure.feign.apple.dto.ApplePublicKeyRes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppleJwtUtils {

    private final AppleClient appleClient;

    public Claims getClaimsBy(String identityToken) {
        try {
            ApplePublicKeyRes response = appleClient.getAppleAuthPublicKey();

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

            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(identityToken).getBody();
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

}

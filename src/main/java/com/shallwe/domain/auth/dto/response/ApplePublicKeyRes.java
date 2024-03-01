package com.shallwe.domain.auth.dto.response;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class ApplePublicKeyRes {

    private List<Key> keys;

    @Data
    public static class Key {
        private String kty;
        private String kid;
        private String use;
        private String alg;
        private String n;
        private String e;
    }

    public Optional<Key> getMatchedKeyBy(String kid, String alg) {
        return this.keys.stream()
                .filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
                .findFirst();
    }

}

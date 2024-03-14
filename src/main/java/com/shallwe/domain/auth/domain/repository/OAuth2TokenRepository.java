package com.shallwe.domain.auth.domain.repository;

import com.shallwe.domain.auth.domain.OAuth2Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuth2TokenRepository extends JpaRepository<OAuth2Token, Long> {

    Optional<OAuth2Token> findByProviderId(String providerId);

}

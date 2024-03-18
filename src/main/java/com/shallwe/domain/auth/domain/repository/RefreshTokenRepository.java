package com.shallwe.domain.auth.domain.repository;

import com.shallwe.domain.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByProviderId(String providerId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
package com.shallwe.domain.auth.domain.repository;

import com.shallwe.domain.auth.domain.OAuth2Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2TokenRepository extends JpaRepository<OAuth2Token, Long> {
}

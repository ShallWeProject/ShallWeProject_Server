package com.shallwe.domain.auth.domain.repository;

import com.shallwe.domain.auth.domain.AppleToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppleTokenRepository extends JpaRepository<AppleToken, Long> {
}

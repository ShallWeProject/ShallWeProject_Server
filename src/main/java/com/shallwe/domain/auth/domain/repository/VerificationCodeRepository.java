package com.shallwe.domain.auth.domain.repository;

import com.shallwe.domain.auth.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByPhoneNumber(String phoneNumber);

}

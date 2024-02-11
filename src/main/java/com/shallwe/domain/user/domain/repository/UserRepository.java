package com.shallwe.domain.user.domain.repository;

import java.util.Optional;

import com.shallwe.domain.common.Status;
import com.shallwe.domain.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndStatus(String email, Status status);

    Boolean existsByEmailAndStatus(String email, Status status);

    Optional<User> findByPhoneNumber(String phoneNumber);

}

package com.shallwe.domain.user.domain.repository;

import java.util.Optional;

import com.shallwe.domain.common.Status;
import com.shallwe.domain.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmailAndStatus(String email, Status status);

    Optional<User> findByPhoneNumberAndStatus(String phoneNumber, Status status);

    Optional<User> findByEmailAndStatus(String email, Status status);

}

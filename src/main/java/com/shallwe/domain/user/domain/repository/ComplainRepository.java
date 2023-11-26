package com.shallwe.domain.user.domain.repository;

import com.shallwe.domain.user.domain.Complain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplainRepository extends JpaRepository<Complain, Long> {
}

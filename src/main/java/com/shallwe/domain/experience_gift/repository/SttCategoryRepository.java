package com.shallwe.domain.experience_gift.repository;

import com.shallwe.domain.experience_gift.domain.SttCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SttCategoryRepository extends JpaRepository<SttCategory,Long> {
}

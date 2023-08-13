package com.shallwe.domain.experience_gift.repository;

import com.shallwe.domain.experience_gift.domain.Subtitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubtitleRepository extends JpaRepository<Subtitle, Long> {
    Optional<Subtitle> findBySubtitleId(Long subtitleId);
}

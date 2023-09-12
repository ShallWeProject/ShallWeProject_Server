package com.shallwe.domain.experienceGift.domain.repository;

import com.shallwe.domain.experienceGift.domain.Subtitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubtitleRepository extends JpaRepository<Subtitle, Long> {
    Optional<Subtitle> findBySubtitleId(Long subtitleId);
}

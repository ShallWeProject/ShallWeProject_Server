package com.shallwe.domain.experiencegift.domain.repository;

import com.shallwe.domain.experiencegift.domain.Subtitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubtitleRepository extends JpaRepository<Subtitle, Long> {
    Optional<Subtitle> findBySubtitleId(Long subtitleId);

    Optional<Subtitle> findByTitle(String subtitle);
}

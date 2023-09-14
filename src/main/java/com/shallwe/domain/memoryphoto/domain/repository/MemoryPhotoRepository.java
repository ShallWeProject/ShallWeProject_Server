package com.shallwe.domain.memoryphoto.domain.repository;

import com.shallwe.domain.memoryphoto.domain.MemoryPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MemoryPhotoRepository extends JpaRepository<MemoryPhoto, Long> {
}

package com.shallwe.domain.memory_photo.domain.repository;

import com.shallwe.domain.memory_photo.domain.MemoryPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryPhotoRepository extends JpaRepository<MemoryPhoto, Long> {
}

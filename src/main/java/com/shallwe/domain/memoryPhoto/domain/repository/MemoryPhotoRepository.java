package com.shallwe.domain.memoryPhoto.domain.repository;

import com.shallwe.domain.memoryPhoto.domain.MemoryPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryPhotoRepository extends JpaRepository<MemoryPhoto, Long> {
}

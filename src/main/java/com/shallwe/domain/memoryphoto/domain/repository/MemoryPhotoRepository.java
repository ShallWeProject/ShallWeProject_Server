package com.shallwe.domain.memoryphoto.domain.repository;

import com.shallwe.domain.memoryphoto.domain.MemoryPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryPhotoRepository extends JpaRepository<MemoryPhoto, Long> {
}

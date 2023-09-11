package com.shallwe.domain.memoryPhoto.application;

import com.shallwe.domain.memoryPhoto.dto.MemoryPhotoDetailRes;
import com.shallwe.global.config.security.token.UserPrincipal;

import java.time.LocalDateTime;
import java.util.List;

public interface MemoryPhotoService {

    List<MemoryPhotoDetailRes> getMemoryPhotoByDate(UserPrincipal userPrincipal, LocalDateTime date);

}

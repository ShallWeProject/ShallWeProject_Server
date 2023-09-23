package com.shallwe.domain.memoryphoto.application;

import com.shallwe.domain.memoryphoto.dto.MemoryPhotoDetailRes;
import com.shallwe.global.config.security.token.UserPrincipal;

import java.time.LocalDate;
import java.util.List;

public interface MemoryPhotoService {

    List<MemoryPhotoDetailRes> getMemoryPhotoByDate(UserPrincipal userPrincipal, LocalDate date);

}

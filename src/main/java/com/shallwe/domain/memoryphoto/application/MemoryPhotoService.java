package com.shallwe.domain.memoryphoto.application;

import com.shallwe.domain.memoryphoto.dto.MemoryPhotoDetailRes;
import com.shallwe.domain.memoryphoto.dto.UploadMemoryPhotoReq;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.Message;

import java.time.LocalDate;
import java.util.List;

public interface MemoryPhotoService {

    List<MemoryPhotoDetailRes> getMemoryPhotoByDate(UserPrincipal userPrincipal, LocalDate date);
    Message uploadMemoryPhoto(UserPrincipal userPrincipal, UploadMemoryPhotoReq uploadMemoryPhotoReq);
    Message deleteMemoryPhoto(UserPrincipal userPrincipal, String memoryPhotoUrl);

}

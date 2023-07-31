package com.shallwe.domain.memory_photo.application;

import com.shallwe.domain.memory_photo.dto.MemoryPhotoDetailRes;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.global.config.security.token.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoryPhotoServiceImpl {

    private final ReservationRepository reservationRepository;

//    public List<MemoryPhotoDetailRes> getMemoryPhotoByDate(UserPrincipal userPrincipal, LocalDate date) {
//        reservationRepository.findAllByDate
//    }

}

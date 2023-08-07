package com.shallwe.domain.memory_photo.application;

import com.shallwe.domain.memory_photo.domain.MemoryPhoto;
import com.shallwe.domain.memory_photo.dto.MemoryPhotoDetailRes;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.reservation.exception.InvalidUserException;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.domain.repository.UserRepository;
import com.shallwe.global.config.security.token.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoryPhotoServiceImpl implements MemoryPhotoService{

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    @Override
    public List<MemoryPhotoDetailRes> getMemoryPhotoByDate(UserPrincipal userPrincipal, LocalDateTime date) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidUserException::new);
        List<Reservation> reservations = reservationRepository.findAllByDateAndPhoneNumber(date, user.getPhoneNumber());

        List<MemoryPhotoDetailRes> memoryPhotoDetailRes = reservations.stream()
                .map(reservation -> MemoryPhotoDetailRes.builder()
                        .reservationId(reservation.getId())
                        .date(reservation.getDate())
                        .memoryPhotoImages(reservation.getMemoryPhotos().stream()
                                .map(MemoryPhoto::getMemoryImgUrl)
                                .toList())
                        .build())
                .toList();

        return memoryPhotoDetailRes;
    }

}

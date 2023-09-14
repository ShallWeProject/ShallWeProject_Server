package com.shallwe.domain.memoryphoto.application;

import com.shallwe.domain.memoryphoto.domain.MemoryPhoto;
import com.shallwe.domain.memoryphoto.domain.repository.MemoryPhotoRepository;
import com.shallwe.domain.memoryphoto.dto.MemoryPhotoDetailRes;
import com.shallwe.domain.memoryphoto.dto.UploadMemoryPhotoReq;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.repository.ReservationRepository;
import com.shallwe.domain.reservation.exception.InvalidReservationException;
import com.shallwe.domain.reservation.exception.InvalidUserException;
import com.shallwe.domain.user.domain.User;
import com.shallwe.domain.user.domain.repository.UserRepository;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.Message;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoryPhotoServiceImpl implements MemoryPhotoService{

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final MemoryPhotoRepository memoryPhotoRepository;

    @Override
    public List<MemoryPhotoDetailRes> getMemoryPhotoByDate(UserPrincipal userPrincipal, LocalDateTime date) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidUserException::new);
        LocalDateTime startDateTime = date.toLocalDate().atStartOfDay(); // 시작 시간 지정
        LocalDateTime endDateTime = date.toLocalDate().plusDays(1).atStartOfDay(); // 끝 시간 지정
        List<Reservation> reservations = reservationRepository.findAllByDateBetweenAndPhoneNumber(startDateTime, endDateTime, user.getPhoneNumber());

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

    @Transactional
    public Message uploadMemoryPhoto(UserPrincipal userPrincipal, UploadMemoryPhotoReq uploadMemoryPhotoReq) {
        Reservation reservation = reservationRepository.findById(uploadMemoryPhotoReq.getReservationId())
                .orElseThrow(InvalidReservationException::new);

        MemoryPhoto memoryPhoto = MemoryPhoto.builder()
                .memoryImgUrl(AwsS3ImageUrlUtil.toUrl(uploadMemoryPhotoReq.getMemoryPhotoImgKey()))
                .reservation(reservation)
                .build();

        memoryPhotoRepository.save(memoryPhoto);

        return Message.builder().message("메모리 포토 업로드가 완료되었습니다.").build();
    }

}

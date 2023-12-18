package com.shallwe.domain.memoryphoto.application;

import com.shallwe.domain.common.Status;
import com.shallwe.domain.memoryphoto.domain.MemoryPhoto;
import com.shallwe.domain.memoryphoto.domain.repository.MemoryPhotoRepository;
import com.shallwe.domain.memoryphoto.dto.MemoryPhotoDetailRes;
import com.shallwe.domain.memoryphoto.dto.UploadMemoryPhotoReq;
import com.shallwe.domain.memoryphoto.exception.MemoryPhotoUploaderMismatchException;
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

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoryPhotoServiceImpl implements MemoryPhotoService{

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final MemoryPhotoRepository memoryPhotoRepository;

    @Override
    public List<MemoryPhotoDetailRes> getMemoryPhotoByDate(final UserPrincipal userPrincipal, final LocalDate date) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidUserException::new);
        List<Reservation> reservations = reservationRepository.findReservationsByDateAndUser(date, user);

        return reservations.stream()
                .map(reservation -> MemoryPhotoDetailRes.toDto(reservation, user))
                .toList();
    }

    @Override
    @Transactional
    public Message uploadMemoryPhoto(UserPrincipal userPrincipal, UploadMemoryPhotoReq uploadMemoryPhotoReq) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(InvalidUserException::new);

        Reservation reservation = reservationRepository.findById(uploadMemoryPhotoReq.getReservationId())
                .orElseThrow(InvalidReservationException::new);

        MemoryPhoto memoryPhoto = MemoryPhoto.builder()
                .memoryImgUrl(AwsS3ImageUrlUtil.toUrl(uploadMemoryPhotoReq.getMemoryPhotoImgKey()))
                .uploader(user)
                .reservation(reservation)
                .build();

        memoryPhotoRepository.save(memoryPhoto);

        return Message.builder().message("메모리 포토 업로드가 완료되었습니다.").build();
    }

    @Override
    @Transactional
    public Message deleteMemoryPhoto(UserPrincipal userPrincipal, String memoryPhotoUrl) {
        MemoryPhoto memoryPhoto = memoryPhotoRepository.findByMemoryImgUrl(memoryPhotoUrl)
                .orElseThrow(InvalidReservationException::new);

        if(!memoryPhoto.getUploader().getId().equals(userPrincipal.getId()))
            throw new MemoryPhotoUploaderMismatchException();

        memoryPhoto.updateStatus(Status.DELETE);

        return Message.builder()
                .message("Memory Photo 삭제가 완료되었습니다.")
                .build();
    }

}

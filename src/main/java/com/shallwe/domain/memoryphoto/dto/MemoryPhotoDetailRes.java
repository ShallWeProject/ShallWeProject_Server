package com.shallwe.domain.memoryphoto.dto;

import com.shallwe.domain.memoryphoto.domain.MemoryPhoto;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MemoryPhotoDetailRes {

    private LocalDate date;

    private LocalTime time;

    private String experienceGiftTitle;

    private String experienceGiftSubTitle;

    private List<MemoryPhotoUserDetailRes> memoryPhotoImages;

    public static MemoryPhotoDetailRes toDto(Reservation reservation, User user) {
        return MemoryPhotoDetailRes.builder()
                .date(reservation.getDate())
                .time(reservation.getTime())
                .experienceGiftTitle(reservation.getExperienceGift().getTitle())
                .experienceGiftSubTitle(reservation.getExperienceGift().getSubtitle().getTitle())
                .memoryPhotoImages(reservation.getMemoryPhotos().stream()
                        .map(memoryPhoto -> MemoryPhotoUserDetailRes.toDto(memoryPhoto.getMemoryImgUrl(), memoryPhoto.getUploader().getId().equals(user.getId())))
                        .toList())
                .build();
    }

}

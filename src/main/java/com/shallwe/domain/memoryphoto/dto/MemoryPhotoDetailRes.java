package com.shallwe.domain.memoryphoto.dto;

import com.shallwe.domain.memoryphoto.domain.MemoryPhoto;
import com.shallwe.domain.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MemoryPhotoDetailRes {

    private LocalDateTime date;

    private String experienceGiftTitle;

    private String experienceGiftSubTitle;

    private List<String> memoryPhotoImages;

    public static MemoryPhotoDetailRes toDto(Reservation reservation) {
        return MemoryPhotoDetailRes.builder()
                .date(reservation.getDate())
                .experienceGiftTitle(reservation.getExperienceGift().getTitle())
                .experienceGiftSubTitle(reservation.getExperienceGift().getSubtitle().getTitle())
                .memoryPhotoImages(reservation.getMemoryPhotos().stream()
                        .map(MemoryPhoto::getMemoryImgUrl)
                        .toList())
                .build();
    }

}

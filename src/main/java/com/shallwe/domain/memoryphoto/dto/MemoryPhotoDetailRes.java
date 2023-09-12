package com.shallwe.domain.memoryphoto.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MemoryPhotoDetailRes {

    private Long reservationId;

    private LocalDateTime date;

    private List<String> memoryPhotoImages;

    @Builder
    public MemoryPhotoDetailRes(Long reservationId, LocalDateTime date, List<String> memoryPhotoImages) {
        this.reservationId = reservationId;
        this.date = date;
        this.memoryPhotoImages = memoryPhotoImages;
    }

}

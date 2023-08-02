package com.shallwe.domain.memory_photo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MemoryPhotoDetailRes {

    private Long reservationId;

    private LocalDate date;

    private List<String> memoryPhotoImages;

}

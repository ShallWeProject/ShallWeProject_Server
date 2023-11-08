package com.shallwe.domain.experiencegift.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AdminMainRes {
    @Schema(type = "LocalDate", description = "현재 날짜", example = "2023-11-07")
    private LocalDate currentDate;
    @Schema(type = "Long", description = "예약 접수 개수", example = "3")
    private Long bookedReservationsCount;

    public static AdminMainRes toDto(LocalDate currentDate, Long bookedReservationsCount) {
        return AdminMainRes.builder()
                .currentDate(currentDate)
                .bookedReservationsCount(bookedReservationsCount)
                .build();
    }

}

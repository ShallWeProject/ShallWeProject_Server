package com.shallwe.domain.reservation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidTimeSlotRes {

  @Schema(description = "예약 ID", type = "long", example = "2")
  private Long reservationId;

  @Schema(description = "날짜", type = "LocalDate", example = "2024-01-07")
  private LocalDate date;

  @Schema(description = "시간", type = "LocalTime", example = "13:00:00")
  private LocalTime time;
}

package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.reservation.domain.ReservationStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationUserReq {

  private Long experienceGiftId;
  private Long persons;
  private LocalDate date;
  private LocalTime time;
  private String phoneNumber;
  private String imageKey;
  private String invitationComment;

  @Builder
  public ReservationUserReq(Long experienceGiftId, Long persons, LocalDate date,
      LocalTime time, String phoneNumber, String imageKey, String invitationComment) {
    this.experienceGiftId = experienceGiftId;
    this.persons = persons;
    this.date = date;
    this.time = time;
    this.phoneNumber = phoneNumber;
    this.imageKey = imageKey;
    this.invitationComment = invitationComment;
  }
}

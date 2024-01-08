package com.shallwe.domain.reservation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserReservationCreate {

  @Schema(description = "경험 선물 ID", example = "1")
  private Long experienceGiftId;

  @Schema(description = "인원 수",example = "3")
  private Long persons;

  @Schema(description = "날짜",example = "2024-01-06")
  private LocalDate date;

  @Schema(description = "시간",example = "13:00:00")
  private LocalTime time;

  @Schema(description = "휴대폰 번호", example = "010-1234-1234")
  private String phoneNumber;

  @Schema(description = "사진 url", example = "https://shallwebucket.s3.ap-northeast-2.amazonaws.com/uploads/000035950024fgultr.jpg")
  private String imageURL;

  @Schema(description = "초대장 메세지", example= "제발 같이 가줘!")
  private String invitationComment;

  @Builder
  public UserReservationCreate(Long experienceGiftId, Long persons, LocalDate date,
      LocalTime time, String phoneNumber, String imageUrl, String invitationComment) {
    this.experienceGiftId = experienceGiftId;
    this.persons = persons;
    this.date = date;
    this.time = time;
    this.phoneNumber = phoneNumber;
    this.imageURL = imageUrl;
    this.invitationComment = invitationComment;
  }
}

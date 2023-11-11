package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class ReservationResponse {

  private Long id;
  private Long senderId;
  private Long ownerId;
  private String sender;
  private Long persons;
  private String date;
  private String time;
  private Long experienceGiftId;
  private String receiver;
  private String phoneNumber;
  private String invitationImageURL;
  private String invitationComment;
  private ReservationStatus reservationStatus;


  public static ReservationResponse toDtoUser(Reservation reservation) {
    ReservationResponseBuilder builder = ReservationResponse.builder()
        .id(reservation.getId())
        .experienceGiftId(reservation.getExperienceGift().getExperienceGiftId())
        .senderId(reservation.getSender().getId())
        .ownerId(reservation.getOwner().getId())
        .sender(reservation.getSender().getName())
        .persons(reservation.getPersons())
        .date(reservation.getDate().toString())
        .time(reservation.getTime().toString())
        .phoneNumber(reservation.getPhoneNumber())
        .receiver(reservation.getReceiver().getName())
        .invitationImageURL(AwsS3ImageUrlUtil.toUrl(reservation.getInvitationImg()))
        .invitationComment(reservation.getInvitationComment())
        .reservationStatus(reservation.getReservationStatus());

    return builder.build();
  }

  public static ReservationResponse toDtoOwner(Reservation reservation) {
    ReservationResponseBuilder builder = ReservationResponse.builder()
        .id(reservation.getId())
        .experienceGiftId(reservation.getExperienceGift().getExperienceGiftId())
        .date(reservation.getDate().toString())
        .time(reservation.getTime().toString())
        .reservationStatus(reservation.getReservationStatus());

    return builder.build();
  }
}

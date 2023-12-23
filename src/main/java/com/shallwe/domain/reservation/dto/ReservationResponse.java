package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.user.domain.User;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;

import java.util.Optional;
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
        .experienceGiftId(reservation.getExperienceGift().getId())
        .senderId(Optional.ofNullable(reservation.getSender()).map(User::getId).orElse(null))
        .ownerId(reservation.getOwner().getId())
        .sender(Optional.ofNullable(reservation.getSender()).map(User::getName).orElse(null))
        .persons(Optional.ofNullable(reservation.getPersons()).orElse(null))
        .date(reservation.getDate().toString())
        .time(reservation.getTime().toString())
        .phoneNumber(Optional.ofNullable(reservation.getPhoneNumber()).orElse(null))
        .receiver(Optional.ofNullable(reservation.getReceiver()).map(User::getName).orElse(null))
        .invitationImageURL(
            Optional.ofNullable(reservation.getInvitationImg()).map(AwsS3ImageUrlUtil::toUrl)
                .orElse(null))
        .invitationComment(Optional.ofNullable(reservation.getInvitationComment()).orElse(null))
        .reservationStatus(reservation.getReservationStatus());

    return builder.build();
  }


  public static ReservationResponse toDtoOwner(Reservation reservation) {
    ReservationResponseBuilder builder = ReservationResponse.builder()
        .id(reservation.getId())
        .experienceGiftId(reservation.getExperienceGift().getId())
        .ownerId(reservation.getOwner().getId())
        .date(reservation.getDate().toString())
        .time(reservation.getTime().toString())
        .reservationStatus(reservation.getReservationStatus());

    return builder.build();
  }

}

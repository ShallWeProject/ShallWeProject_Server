package com.shallwe.domain.user.dto;

import com.shallwe.domain.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import com.shallwe.domain.reservation.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class SendGiftDetailRes {

    private Long reservationId;
    private ReservationStatus reservationStatus;
    private String experienceTitle;
    private String experienceSubTitle;
    private LocalDate date;
    private LocalTime time;
    private UserDetailRes receiver;
    private String invitationImg;
    private String invitationComment;

    public static SendGiftDetailRes toDto(Reservation reservation) {
        return SendGiftDetailRes.builder()
                .reservationId(reservation.getId())
                .reservationStatus(reservation.getReservationStatus())
                .experienceTitle(reservation.getExperienceGift().getTitle())
                .experienceSubTitle(reservation.getExperienceGift().getSubtitle().getTitle())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .receiver(UserDetailRes.toDto(reservation.getReceiver()))
                .invitationImg(reservation.getInvitationImg())
                .invitationComment(reservation.getInvitationComment())
                .build();
    }

}

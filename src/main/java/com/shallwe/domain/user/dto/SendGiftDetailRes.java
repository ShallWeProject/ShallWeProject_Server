package com.shallwe.domain.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.shallwe.domain.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import com.shallwe.domain.reservation.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SendGiftDetailRes {

    private Long reservationId;
    private ReservationStatus reservationStatus;
    private Long experienceGiftId;
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
                .experienceGiftId(reservation.getExperienceGift().getId())
                .experienceTitle(reservation.getExperienceGift().getTitle())
                .experienceSubTitle(reservation.getExperienceGift().getSubtitle().getTitle())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .receiver(UserDetailRes.toDto(reservation.getReceiver()))
                .invitationImg(reservation.getInvitationImg())
                .invitationComment(reservation.getInvitationComment())
                .build();
    }

    @Builder
    @QueryProjection
    public SendGiftDetailRes(Long reservationId, ReservationStatus reservationStatus, Long experienceGiftId, String experienceTitle, String experienceSubTitle, LocalDate date, LocalTime time, UserDetailRes receiver, String invitationImg, String invitationComment) {
        this.reservationId = reservationId;
        this.reservationStatus = reservationStatus;
        this.experienceGiftId = experienceGiftId;
        this.experienceTitle = experienceTitle;
        this.experienceSubTitle = experienceSubTitle;
        this.date = date;
        this.time = time;
        this.receiver = receiver;
        this.invitationImg = invitationImg;
        this.invitationComment = invitationComment;
    }

}

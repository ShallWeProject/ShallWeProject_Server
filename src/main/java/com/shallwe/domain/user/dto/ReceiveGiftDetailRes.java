package com.shallwe.domain.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.shallwe.domain.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

import com.shallwe.domain.reservation.domain.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReceiveGiftDetailRes {

    private Long reservationId;
    private ReservationStatus reservationStatus;
    private Long experienceGiftId;
    private String experienceTitle;
    private String experienceSubTitle;
    private LocalDate date;
    private LocalTime time;
    private UserDetailRes sender;
    private String invitationImg;
    private String invitationComment;

    public static ReceiveGiftDetailRes toDto(Reservation reservation) {
        return ReceiveGiftDetailRes.builder()
                .reservationId(reservation.getId())
                .reservationStatus(reservation.getReservationStatus())
                .experienceGiftId(reservation.getExperienceGift().getId())
                .experienceTitle(reservation.getExperienceGift().getTitle())
                .experienceSubTitle(reservation.getExperienceGift().getSubtitle().getTitle())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .sender(UserDetailRes.toDto(reservation.getSender()))
                .invitationImg(reservation.getInvitationImg())
                .invitationComment(reservation.getInvitationComment())
                .build();
    }

    @Builder
    @QueryProjection
    public ReceiveGiftDetailRes(Long reservationId, ReservationStatus reservationStatus, Long experienceGiftId, String experienceTitle, String experienceSubTitle, LocalDate date, LocalTime time, UserDetailRes sender, String invitationImg, String invitationComment) {
        this.reservationId = reservationId;
        this.reservationStatus = reservationStatus;
        this.experienceGiftId = experienceGiftId;
        this.experienceTitle = experienceTitle;
        this.experienceSubTitle = experienceSubTitle;
        this.date = date;
        this.time = time;
        this.sender = sender;
        this.invitationImg = invitationImg;
        this.invitationComment = invitationComment;
    }

}

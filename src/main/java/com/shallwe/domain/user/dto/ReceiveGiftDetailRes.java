package com.shallwe.domain.user.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ReceiveGiftDetailRes {

    private Long reservationId;
    private String experienceTitle;
    private String experienceSubTitle;
    private LocalDateTime dateTime;
    private UserDetailRes sender;
    private String invitationImg;
    private String invitationComment;

    public static ReceiveGiftDetailRes toDto(Reservation reservation) {
        return ReceiveGiftDetailRes.builder()
                .reservationId(reservation.getId())
                .experienceTitle(reservation.getExperienceGift().getTitle())
                .experienceSubTitle(reservation.getExperienceGift().getSubtitle().getTitle())
                .dateTime(reservation.getDate())
                .sender(UserDetailRes.toDto(reservation.getSender()))
                .invitationImg(reservation.getInvitationImg())
                .invitationComment(reservation.getInvitationComment())
                .build();
    }

}

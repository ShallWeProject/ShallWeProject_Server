package com.shallwe.domain.user.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class SendGiftDetailRes {

    private Long reservationId;
    private String experienceTitle;
    private String experienceSubTitle;
    private LocalDateTime dateTime;
    private UserDetailRes receiver;
    private String invitationImg;
    private String invitationComment;

    public static SendGiftDetailRes toDto(Reservation reservation) {
        return SendGiftDetailRes.builder()
                .reservationId(reservation.getId())
                .experienceTitle(reservation.getExperienceGift().getTitle())
                .experienceSubTitle(reservation.getExperienceGift().getSubtitle().getTitle())
                .dateTime(reservation.getDate())
                .receiver(UserDetailRes.toDto(reservation.getReceiver()))
                .invitationImg(reservation.getInvitationImg())
                .invitationComment(reservation.getInvitationComment())
                .build();
    }

}

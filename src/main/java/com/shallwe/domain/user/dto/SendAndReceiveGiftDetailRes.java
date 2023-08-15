package com.shallwe.domain.user.dto;

import com.shallwe.domain.experience_gift.dto.response.ExperienceDetailRes;
import com.shallwe.domain.reservation.domain.Reservation;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SendAndReceiveGiftDetailRes {

    private Long reservationId;
    private ExperienceDetailRes experienceGift;
    private UserDetailRes sender;
    private Long persons;
    private LocalDateTime dateTime;
    private UserDetailRes receiver;
    private String invitationImg;
    private String invitationComment;

    @Builder
    public SendAndReceiveGiftDetailRes(Long reservationId, ExperienceDetailRes experienceGift, UserDetailRes sender, Long persons, LocalDateTime dateTime, UserDetailRes receiver, String invitationImg, String invitationComment) {
        this.reservationId = reservationId;
        this.experienceGift = experienceGift;
        this.sender = sender;
        this.persons = persons;
        this.dateTime = dateTime;
        this.receiver = receiver;
        this.invitationImg = invitationImg;
        this.invitationComment = invitationComment;
    }

    public static SendAndReceiveGiftDetailRes toDto(Reservation reservation) {
        return SendAndReceiveGiftDetailRes.builder()
                .reservationId(reservation.getId())
                .experienceGift(ExperienceDetailRes.toDto(reservation.getExperienceGift()))
                .sender(UserDetailRes.toDto(reservation.getSender()))
                .persons(reservation.getPersons())
                .dateTime(reservation.getDate())
                .receiver(UserDetailRes.toDto(reservation.getReceiver()))
                .invitationImg(reservation.getInvitationImg())
                .invitationComment(reservation.getInvitationComment())
                .build();
    }

}

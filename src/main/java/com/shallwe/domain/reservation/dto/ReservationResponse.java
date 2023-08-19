package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.global.utils.AwsS3ImageUrlUtil;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@Data
public class ReservationResponse {

    private Long id;
    private Long senderId;
    private String sender;
    private Long persons;
    private LocalDateTime date;
    private Long experienceGiftId;
    private String receiver;
    private String phoneNumber;
    private String invitationImageURL;
    private String invitationComment;
    private ReservationStatus reservationStatus;

    @Builder
    public ReservationResponse(Long id, Long senderId, String sender, Long persons, LocalDateTime date, Long experienceGiftId, String receiver, String phoneNumber, String invitationImageURL, String invitationComment, ReservationStatus reservationStatus) {
        this.id = id;
        this.senderId = senderId;
        this.sender = sender;
        this.persons = persons;
        this.date = date;
        this.experienceGiftId = experienceGiftId;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.invitationImageURL = invitationImageURL;
        this.invitationComment = invitationComment;
        this.reservationStatus = reservationStatus;
    }

    public static ReservationResponse toDto(Reservation reservation) {
        ReservationResponseBuilder builder = ReservationResponse.builder()
                .id(reservation.getId())
                .experienceGiftId(reservation.getExperienceGift().getExperienceGiftId())
                .senderId(reservation.getSender().getId())
                .sender(reservation.getSender().getName())
                .persons(reservation.getPersons())
                .date(reservation.getDate())
                .phoneNumber(reservation.getPhoneNumber())
                .receiver(reservation.getReceiver().getName())
                .invitationImageURL(AwsS3ImageUrlUtil.toUrl(reservation.getInvitationImg()))
                .invitationComment(reservation.getInvitationComment())
                .reservationStatus(reservation.getReservationStatus());

        return builder.build();
    }

    public static ReservationResponse fromReservation(Reservation reservation) {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setId(reservation.getId());
        reservationResponse.setSenderId(reservation.getSender().getId());
        reservationResponse.setSender(reservation.getSender().getName());
        reservationResponse.setPersons(reservation.getPersons());
        reservationResponse.setDate(reservation.getDate());
        reservationResponse.setExperienceGiftId(reservation.getExperienceGift().getExperienceGiftId());
        reservationResponse.setReceiver(reservation.getReceiver().getName());
        reservationResponse.setPhoneNumber(reservation.getPhoneNumber());
        reservationResponse.setInvitationImageURL(AwsS3ImageUrlUtil.toUrl(reservation.getInvitationImg()));
        reservationResponse.setInvitationComment(reservation.getInvitationComment());
        reservationResponse.setReservationStatus(reservation.getReservationStatus());

        return reservationResponse;
    }

}

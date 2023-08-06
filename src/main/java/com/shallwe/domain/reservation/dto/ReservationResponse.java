package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@Data
public class ReservationResponse {

    private Long id;
    private Long userId;
    private String user;
    private Long persons;
    private LocalDateTime date;
    private Long experienceGiftId;
    private String receiver;
    private String phone_number;
    private String invitationImg;
    private String invitationComment;
    private ReservationStatus reservationStatus;

    @Builder
    public ReservationResponse(Long id, Long userId, String user, Long persons, LocalDateTime date, Long experienceGiftId, String receiver, String phone_number, String invitationImg, String invitationComment, ReservationStatus reservationStatus) {
        this.id = id;
        this.userId = userId;
        this.persons = persons;
        this.date = date;
        this.experienceGiftId = experienceGiftId;
        this.receiver = receiver;
        this.phone_number = phone_number;
        this.invitationImg = invitationImg;
        this.invitationComment = invitationComment;
        this.reservationStatus = reservationStatus;
    }

    public static ReservationResponse toDto(Reservation reservation) {
        ReservationResponseBuilder builder = ReservationResponse.builder()
                .id(reservation.getId())
                .experienceGiftId(reservation.getExperienceGift().getExperienceGiftId())
                .userId(reservation.getUser().getId())
                .user(reservation.getUser().getName())
                .persons(reservation.getPersons())
                .date(reservation.getDate())
                .phone_number(reservation.getPhone_number())
                .receiver(reservation.getReceiver())
                .invitationImg(reservation.getInvitationImg())
                .invitationComment(reservation.getInvitationComment())
                .reservationStatus(reservation.getReservationStatus());

        return builder.build();
    }

    public static ReservationResponse fromReservation(Reservation reservation) {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setId(reservation.getId());
        reservationResponse.setUserId(reservation.getUser().getId());
        reservationResponse.setUser(reservation.getUser().getName());
        reservationResponse.setPersons(reservation.getPersons());
        reservationResponse.setDate(reservation.getDate());
        reservationResponse.setExperienceGiftId(reservation.getExperienceGift().getExperienceGiftId());
        reservationResponse.setReceiver(reservation.getReceiver());
        reservationResponse.setPhone_number(reservation.getPhone_number());
        reservationResponse.setInvitationImg(reservation.getInvitationImg());
        reservationResponse.setInvitationComment(reservation.getInvitationComment());
        reservationResponse.setReservationStatus(reservation.getReservationStatus());

        return reservationResponse;
    }

}

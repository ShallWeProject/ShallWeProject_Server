package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.Reservation_status;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ReservationResponse {
    private Long id;
    private Long userId;
    private Long experienceGiftId;
    private Long persons;
    private LocalDateTime date;
    private String sender;
    private String receiver;
    private String phone_number;
    private String invitation_img;
    private String invitation_comment;
    private Reservation_status reservation_status;

    @Builder
    public ReservationResponse(Long id, Long experienceGiftId, Long userId, Long persons, LocalDateTime date, String sender, String receiver, String phone_number, String invitation_img, String invitation_comment, Reservation_status reservation_status) {
        this.experienceGiftId = experienceGiftId;
        this.id = id;
        this.userId = userId;
        this.persons = persons;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
        this.phone_number = phone_number;
        this.invitation_img = invitation_img;
        this.invitation_comment = invitation_comment;
        this.reservation_status = reservation_status;
    }

    public static ReservationResponse toDto(Reservation reservation) {
        ReservationResponseBuilder builder = ReservationResponse.builder()
                .id(reservation.getId())
                .experienceGiftId(reservation.getExperienceGift().getExperienceGiftId())
                .userId(reservation.getUser().getId())
                .persons(reservation.getPersons())
                .date(reservation.getDate())
                .phone_number(reservation.getPhone_number())
                .sender(reservation.getUser().getName())
                .receiver(reservation.getReceiver())
                .invitation_img(reservation.getInvitation_img())
                .invitation_comment(reservation.getInvitation_comment())
                .reservation_status(reservation.getReservation_status());

        return builder.build();
    }

    public static ReservationResponse fromReservation(Reservation reservation) {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setId(reservation.getId());
        reservationResponse.setUserId(reservationResponse.getUserId());
        reservationResponse.setPersons(reservation.getPersons());
        reservationResponse.setDate(reservation.getDate());
        reservationResponse.setSender(reservation.getSender());
        reservationResponse.setReceiver(reservation.getReceiver());
        reservationResponse.setPhone_number(reservation.getPhone_number());
        reservationResponse.setInvitation_img(reservation.getInvitation_img());
        reservationResponse.setInvitation_comment(reservation.getInvitation_comment());
        reservationResponse.setReservation_status(reservation.getReservation_status());

        return reservationResponse;
    }
}

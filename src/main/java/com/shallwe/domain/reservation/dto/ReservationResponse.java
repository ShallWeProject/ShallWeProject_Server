package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.Reservation_status;
import com.shallwe.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ReservationResponse {
    private Long id;
    private Long userId;
    private String user;
    //private Long gift_id;
    private Long persons;
    private LocalDateTime date;
    private String receiver;
    private String phone_number;
    private String invitation_img;
    private String invitation_comment;
    private Reservation_status reservation_status;

    @Builder
    public ReservationResponse(Long id, /*Long gift_id*/ Long userId, String user, Long persons, LocalDateTime date, String receiver, String phone_number, String invitation_img, String invitation_comment, Reservation_status reservation_status) {
//      this.gift_id = gift_id;
        this.id = id;
        this.userId = userId;
        this.user = user;
        this.persons = persons;
        this.date = date;
        this.receiver = receiver;
        this.phone_number = phone_number;
        this.invitation_img = invitation_img;
        this.invitation_comment = invitation_comment;
        this.reservation_status = reservation_status;
    }

    public static ReservationResponse toDto(Reservation reservation) {
        ReservationResponseBuilder builder = ReservationResponse.builder()
                .id(reservation.getId())
//              .gift_id(reservation.getgift_id())
                .userId(reservation.getUser().getId())
                .user(reservation.getUser().getName())
                .persons(reservation.getPersons())
                .date(reservation.getDate())
                .phone_number(reservation.getPhone_number())
                .receiver(reservation.getReceiver())
                .invitation_img(reservation.getInvitation_img())
                .invitation_comment(reservation.getInvitation_comment())
                .reservation_status(reservation.getReservation_status());

        return builder.build();
    }

    public static ReservationResponse fromReservation(Reservation reservation) {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setId(reservation.getId());
        reservationResponse.setUserId(reservation.getUser().getId());
        reservationResponse.setUser(reservation.getUser().getName());
        reservationResponse.setPersons(reservation.getPersons());
        reservationResponse.setDate(reservation.getDate());
        reservationResponse.setReceiver(reservation.getReceiver());
        reservationResponse.setPhone_number(reservation.getPhone_number());
        reservationResponse.setInvitation_img(reservation.getInvitation_img());
        reservationResponse.setInvitation_comment(reservation.getInvitation_comment());
        reservationResponse.setReservation_status(reservation.getReservation_status());

        return reservationResponse;
    }
}

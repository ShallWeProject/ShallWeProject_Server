package com.shallwe.domain.reservation.dto;

import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.domain.ReservationStatus;
import com.shallwe.domain.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

import static com.shallwe.domain.reservation.domain.ReservationStatus.BOOKED;



@Data
public class ReservationRequest {

    private Long experienceGiftId;
    private Long userid;
    private Long persons;
    private LocalDateTime date;
    private String sender;
    private String receiver;
    private String phoneNumber;
    private String invitationImg;
    private String invitationComment;
    private ReservationStatus reservationStatus;

    public static Reservation toEntity(final ReservationRequest reservationRequest, final User user) {
        try {
            Reservation toEntity = Reservation.builder()
                    .user(user)
                    .persons(reservationRequest.getPersons())
                    .date(reservationRequest.getDate())
                    .receiver(reservationRequest.getReceiver())
                    .phoneNumber(reservationRequest.getPhoneNumber())
                    .invitationImg(reservationRequest.getInvitationImg())
                    .invitationComment(reservationRequest.getInvitationComment())
                    .reservationStatus(BOOKED)
                    .build();
            return toEntity;
        } catch (Exception e) {
            System.err.println("예외 발생: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}

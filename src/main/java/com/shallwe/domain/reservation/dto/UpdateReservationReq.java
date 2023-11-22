package com.shallwe.domain.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;


@Data
public class UpdateReservationReq {

    private Long id;
    private Long persons;
    private LocalDate date;
    private LocalTime time;
    private String phone_number;
    private String invitation_img;
    private String invitation_comment;

}

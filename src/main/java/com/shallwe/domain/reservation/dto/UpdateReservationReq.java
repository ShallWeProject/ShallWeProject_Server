package com.shallwe.domain.reservation.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class UpdateReservationReq {
    private Long id;
    private Long persons;
    private LocalDateTime date;
    private String receiver;
    private String phone_number;
    private String invitation_img;
    private String invitation_comment;
}

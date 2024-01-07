package com.shallwe.domain.reservation.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class DeleteReservationRes {

    private String message;

    @Builder
    public DeleteReservationRes(String message){this.message=message;}
    public static DeleteReservationRes toDTO(){
        DeleteReservationResBuilder builder = DeleteReservationRes.builder()
                .message("예약 삭제되었습니다.");
        return builder.build();
    }

}

package com.shallwe.domain.reservation.presentation;

import com.shallwe.domain.reservation.application.ReservationServiceImpl;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.dto.ReservationResponse;
import com.shallwe.global.payload.ErrorResponse;
import com.shallwe.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationServiceImpl reservationServiceimpl;

    @Operation(summary = "예약 정보 불러오기 ", description = "저장된 모든 예약 정보")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 정보 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Reservation.class)))}),
            @ApiResponse(responseCode = "400", description = "예약 정보 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),

    })
    @GetMapping
    public ResponseCustom<List<ReservationResponse>> getAllReservations(){
        return ResponseCustom.OK(reservationServiceimpl.getAllReservation());
    }

    /*@Operation(summary ="예약 추가하기", description = "현재 유저, 경험을 가져와 예약을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "예약 생성 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class))}),
            @ApiResponse(responseCode = "400", description = "예약 생성 실패", )

    })*/
}
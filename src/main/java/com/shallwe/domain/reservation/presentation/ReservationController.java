package com.shallwe.domain.reservation.presentation;

import com.shallwe.domain.reservation.application.ReservationService;
import com.shallwe.domain.reservation.application.ReservationServiceImpl;
import com.shallwe.domain.reservation.domain.Reservation;
import com.shallwe.domain.reservation.dto.ReservationRequest;
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
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary="예약 정보 불러오기", description = "유저 ID로 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "해당 유저 예약 정보 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Reservation.class)))}),
            @ApiResponse(responseCode = "400", description = "해당 유저 예약 정보 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),

    })
    @GetMapping("/user/{userId}")
    public ResponseCustom<List<ReservationResponse>> getUserReservations(@PathVariable Long userId){
        return ResponseCustom.OK(reservationServiceimpl.findUserReservation(userId));
    }


}

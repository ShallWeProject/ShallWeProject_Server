package com.shallwe.domain.reservation.presentation;

import com.shallwe.domain.reservation.application.ReservationCheckService;
import com.shallwe.domain.reservation.application.ReservationManipulationService;
import com.shallwe.domain.reservation.dto.response.DeleteReservationRes;
import com.shallwe.domain.reservation.dto.response.ReservationIdUserRes;
import com.shallwe.domain.reservation.dto.response.ReservationResponse;
import com.shallwe.domain.reservation.dto.request.UserReservationCreate;
import com.shallwe.domain.reservation.dto.request.UpdateReservationReq;
import com.shallwe.domain.reservation.dto.response.ValidTimeSlotRes;
import com.shallwe.global.config.security.token.CurrentUser;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.ErrorResponse;
import com.shallwe.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reservations", description = "Reservations API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationCheckService reservationCheckService;
    private final ReservationManipulationService reservationManipulationService;

    @Operation(summary = "예약 정보 불러오기 ", description = "저장된 모든 예약 정보")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 정보 조회 성공", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReservationResponse.class)))}),
            @ApiResponse(responseCode = "400", description = "예약 정보 조회 실패", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),

    })
    @GetMapping
    public ResponseCustom<List<ReservationResponse>> getAllReservations() {
        return ResponseCustom.OK(reservationCheckService.getAllReservation());
    }

    @Operation(summary = "예약 정보 불러오기", description = "유저 ID로 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "해당 유저 예약 정보 조회 성공", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReservationResponse.class)))}),
            @ApiResponse(responseCode = "400", description = "해당 유저 예약 정보 조회 실패", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),

    })
    @GetMapping("/user")
    public ResponseCustom<List<ReservationResponse>> getUserReservations(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) {
        return ResponseCustom.OK(reservationCheckService.findUserReservation(userPrincipal));
    }

    @Operation(summary = "가능한 예약 불러오기", description = "상품 ID로 검색")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "해당 유저 예약 정보 조회 성공", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ValidTimeSlotRes.class)))}),
            @ApiResponse(responseCode = "400", description = "해당 유저 예약 정보 조회 실패", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),

    })
    @GetMapping("/validTimes")
    public ResponseCustom<List<ValidTimeSlotRes>> getValidReservations(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "ExperienceGift ID를 입력해주세요.", required = true) @RequestParam Long giftId
    ) {
        return ResponseCustom.OK(reservationCheckService.getValidReservationTime(userPrincipal, giftId));
    }

    @Operation(summary = "날짜로 예약 조회", description = "등록한 상품의 ID와 날짜로 이용 가능한 예약을 조회 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 예약 조회 성공", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReservationResponse.class)))}),
            @ApiResponse(responseCode = "400", description = "상품 예약 조회 실패", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/date")
    public ResponseCustom<List<ReservationIdUserRes>> getReservationWithDate(

            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "상품 ID를 입력해주세요", required = true) @RequestParam Long giftId,
            @Parameter(description = "조회하려는 날짜를 입력해주세요 YYYY-MM-DD ", required = true) @RequestParam LocalDate date
    ) {
        return ResponseCustom.OK(
                reservationCheckService.getReservationByDateUser(userPrincipal, giftId, date));
    }

    @Operation(summary = "해당 경험 선물에 생성된 예약 조회 ", description = "경험 ID로 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 정보 조회 성공", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReservationIdUserRes.class)))}),
            @ApiResponse(responseCode = "400", description = "예약 정보 조회 실패", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),

    })
    @GetMapping("/giftId")
    public ResponseCustom<List<ReservationResponse>> getCurrentGiftReservation(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @RequestHeader Long giftId
    ) {
        return ResponseCustom.OK(reservationCheckService.getCurrentGiftReservation(giftId));
    }

    @Operation(summary = "유저 예약 추가하기", description = "등록된 예약이 예약 가능한지 확인 후, 예약을 확정 상태로 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "예약 생성 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "예약 생성 실패", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/user")
    public ResponseCustom<ReservationResponse> createUserReservation(
            @Parameter(description = "예약 요청을 확인해주세요.", required = true) @RequestBody UserReservationCreate reservationRequest,
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) throws Exception {
        return ResponseCustom.CREATED(
                reservationManipulationService.addUserReservation(reservationRequest, userPrincipal));
    }

    @Operation(summary = "예약 수정하기", description = "예약 ID로 해당 예약의 시간을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "예약 수정 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "예약 수정 실패", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping
    public ResponseCustom<ReservationResponse> updateReservation(
            @Parameter(description = "수정 요청을 확인해주세요.", required = true) @RequestBody UpdateReservationReq updateReq,
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) throws Exception {
        return ResponseCustom.OK(
                reservationManipulationService.updateReservation(updateReq, userPrincipal));

    }

    @Operation(summary = "예약 취소하기", description = "예약을 취소합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "예약 취소 성공"),
            @ApiResponse(responseCode = "400", description = "예약 취소 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PatchMapping("/{reservation-id}")
    public ResponseEntity<Void> cancelReservation(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(name = "reservationId", description = "예약 ID를 확인해주세요.", required = true) @PathVariable(name = "reservation-id") Long reservationId
    ) throws Exception {
        reservationManipulationService.cancelReservation(userPrincipal, reservationId);
        return ResponseEntity.noContent().build();
    }

}

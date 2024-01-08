package com.shallwe.domain.shopowner.presentation;


import com.shallwe.domain.reservation.application.ReservationCheckService;
import com.shallwe.domain.reservation.application.ReservationManipulationService;
import com.shallwe.domain.reservation.dto.response.ReservationIdOwnerRes;

import com.shallwe.domain.reservation.dto.request.OwnerReservationCreate;
import com.shallwe.domain.reservation.dto.response.ReservationResponse;
import com.shallwe.domain.reservation.dto.response.ValidTimeSlotRes;
import com.shallwe.domain.shopowner.application.ShopOwnerServiceImpl;
import com.shallwe.domain.shopowner.dto.ShopOwnerIdentificationReq;

import com.shallwe.global.config.security.token.CurrentUser;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.ErrorResponse;
import com.shallwe.global.payload.Message;
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
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ShopOwners", description = "ShopOwners API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/shop-owners")
public class ShopOwnerController {

  private final ShopOwnerServiceImpl shopOwnerService;
  private final ReservationCheckService reservationCheckServiceService;
  private final ReservationManipulationService reservationManipulationService;

  @Operation(summary ="예약 추가하기", description = "현재 유저, 경험을 가져와 예약을 추가합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "예약 생성 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class))}),
      @ApiResponse(responseCode = "400", description = "예약 생성 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))} )
  })
  @PostMapping("/")
  public ResponseCustom<List<ReservationResponse>> createReservation(
      @Parameter(description = "예약 요청을 확인해주세요.", required = true) @RequestBody OwnerReservationCreate ownerReservationCreate,
      @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
  ){
    return ResponseCustom.CREATED(reservationManipulationService.addOwnerReservation(
        ownerReservationCreate,userPrincipal));
  }

    @Operation(summary = "사장 탈퇴", description = "사장 탈퇴를 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장 탈퇴 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "사장 탈퇴 실패", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PatchMapping
    public ResponseCustom<Message> deleteCurrentShopOwner(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) {
        return ResponseCustom.OK(shopOwnerService.deleteCurrentShopOwner(userPrincipal));
    }

    @Operation(summary = "사장 신분증/사업자등록증/통장사본 등록", description = "사장 신분증/사업자등록증/통장사본 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장 신분증/사업자등록증/통장사본 등록 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "사장 신분증/사업자등록증/통장사본 등록 실패", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping("/identification")
    public ResponseCustom<Message> uploadShopOwnerIdentification(
            @Parameter(description = "ShopOwnerIdentificationReq Schema를 확인해 주세요.", required = true) @RequestBody ShopOwnerIdentificationReq shopOwnerIdentificationReq,
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) {
        return ResponseCustom.OK(shopOwnerService.uploadShopOwnerIdentification(shopOwnerIdentificationReq, userPrincipal));
    }

    @Operation(summary = "사장 예약 조회", description = "사장이 등록한 상품의 예약을 조회 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 예약 조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "상품 예약 조회 실패", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping
    public ResponseCustom<List<ValidTimeSlotRes>> getCurrentGiftReservation(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "상품 ID를 입력해주세요", required = true) @RequestParam Long giftId
    ) {
        return ResponseCustom.OK(shopOwnerService.getShopOwnerReservation(userPrincipal, giftId));
    }

  @Operation(summary = "날짜로 이용 가능한 예약 조회", description = "사장이 등록한 상품의 예약을 조회 합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "상품 예약 조회 성공", content = {
          @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReservationResponse.class)))}),
      @ApiResponse(responseCode = "400", description = "상품 예약 조회 실패", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
  })
  @GetMapping("/date")
  public ResponseCustom<List<ReservationIdOwnerRes>> getReservationWithDate(
      @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
      @Parameter(description = "상품 ID를 입력해주세요", required = true) @RequestParam Long giftId,
      @Parameter(description = "조회하려는 날짜를 입력해주세요 YYYY-MM-DD ", required = true) @RequestParam LocalDate date
  ) {
    return ResponseCustom.OK(reservationCheckServiceService.getReservationByDateOwner(userPrincipal, giftId,date));

    }

    @Operation(summary = "예약 확정", description = "유저 결제 확인 후 예약을 확정상태로 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 확정 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "예약 확정 실패", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping("/confirm")
    public ResponseCustom<Message> confirmReservationPayment(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "예약 ID를 입력해주세요", required = true) @RequestParam Long reservationId
    ) {
        return ResponseCustom.OK(shopOwnerService.confirmPayment(userPrincipal, reservationId));
    }

}

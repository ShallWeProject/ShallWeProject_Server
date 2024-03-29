package com.shallwe.domain.user.presentation;

import com.shallwe.domain.user.application.UserServiceImpl;
import com.shallwe.domain.user.dto.*;
import com.shallwe.global.config.security.token.CurrentUser;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.ResponseCustom;
import com.shallwe.global.payload.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Users API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Operation(summary = "유저 정보 확인", description = "현재 접속된 유저정보를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 확인 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailRes.class))}),
            @ApiResponse(responseCode = "400", description = "유저 확인 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping
    public ResponseCustom<UserDetailRes> getCurrentUser(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) {
        return ResponseCustom.OK(userServiceImpl.getCurrentUser(userPrincipal));
    }

    @Operation(summary = "유저 탈퇴", description = "현재 유저를 탈퇴 처리 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 탈퇴 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DeleteUserRes.class))}),
            @ApiResponse(responseCode = "400", description = "유저 탈퇴 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping("/inactive")
    public ResponseEntity<Void> inactiveCurrentUser(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "PostComplainReq를 확인 해 주세요.", required = true) @RequestBody PostComplainReq postComplainReq
    ) {
        userServiceImpl.inactiveCurrentUser(userPrincipal, postComplainReq);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "유저 세부정보 입력", description = "이름, 마켓팅 정보 동의와 나이, 성별 정보를 입력받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보 입력 성공"),
            @ApiResponse(responseCode = "400", description = "유저 정보 입력 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PatchMapping
    public ResponseEntity<Void> signUpCurrentUser(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "SignUpUserReq 를 확인해주세요.", required = true) @RequestBody SignUpUserReq signUpUserReq
    ) {
        userServiceImpl.signUpCurrentUser(userPrincipal, signUpUserReq);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "유저가 보낸 선물 조회", description = "유저가 보낸 선물들을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저가 선물한 리스트 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SendGiftDetailRes.class)))}),
            @ApiResponse(responseCode = "400", description = "유저가 선물한 리스트 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/gift/send")
    public ResponseCustom<List<SendGiftDetailRes>> findSendGiftsByUser(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) {
        return ResponseCustom.OK(userServiceImpl.findSendGiftsByUser(userPrincipal));
    }

    @Operation(summary = "유저가 받은 선물 조회", description = "유저가 받은 선물들을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저가 선물받은 리스트 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReceiveGiftDetailRes.class)))}),
            @ApiResponse(responseCode = "400", description = "유저가 선물받은 리스트 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/gift/receive")
    public ResponseCustom<List<ReceiveGiftDetailRes>> findReceiveGiftsByUser(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) {
        return ResponseCustom.OK(userServiceImpl.findReceiveGiftsByUser(userPrincipal));
    }

}
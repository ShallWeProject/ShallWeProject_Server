package com.shallwe.domain.auth.presentation;


import com.shallwe.domain.auth.dto.request.*;
import com.shallwe.domain.auth.dto.response.SignInRes;
import com.shallwe.domain.auth.dto.response.AuthRes;
import com.shallwe.domain.auth.dto.response.SmsResponseDto;
import com.shallwe.global.infrastructure.sms.NaverSmsClient;
import com.shallwe.global.infrastructure.sms.dto.NaverVerifySmsReq;
import com.shallwe.global.payload.ResponseCustom;
import jakarta.validation.Valid;

import com.shallwe.global.payload.ErrorResponse;
import com.shallwe.global.config.security.token.CurrentUser;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.Message;
import com.shallwe.domain.auth.application.AuthService;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.RequiredArgsConstructor;

@Tag(name = "Authorization", description = "Authorization API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final NaverSmsClient naverSmsClient;

    @Operation(summary = "카카오/구글 로그인", description = "카카오/구글 로그인을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카카오/구글 로그인 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SignInRes.class) ) } ),
            @ApiResponse(responseCode = "400", description = "카카오/구글 로그인 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value="/sign-in")
    public ResponseCustom<SignInRes> signIn(
            @Parameter(description = "SignInReq Schema를 확인해주세요.", required = true) @RequestBody SignInReq signInReq
    ) {
        return ResponseCustom.OK(authService.kakaoGoogleSignIn(signInReq));
    }

    @Operation(summary = "애플 로그인", description = "애플 로그인을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "애플 로그인 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SignInRes.class) ) } ),
            @ApiResponse(responseCode = "400", description = "애플 로그인 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value="/sign-in/apple")
    public ResponseCustom<SignInRes> appleSignIn(
            @Parameter(description = "SignInReq Schema를 확인해주세요.", required = true) @RequestBody AppleSignInReq appleSignInReq
    ) {
        return ResponseCustom.OK(authService.appleSignIn(appleSignInReq));
    }

    @Operation(summary = "토큰 갱신", description = "신규 토큰 갱신을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 갱신 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AuthRes.class) ) } ),
            @ApiResponse(responseCode = "400", description = "토큰 갱신 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value = "/refresh")
    public ResponseCustom<AuthRes> refresh(
            @Parameter(description = "Schemas의 RefreshTokenRequest를 참고해주세요.", required = true) @Valid @RequestBody RefreshTokenReq tokenRefreshRequest
    ) {
        return ResponseCustom.OK(authService.refresh(tokenRefreshRequest));
    }

    @Operation(summary = "유저 로그아웃", description = "유저 로그아웃을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", description = "로그아웃 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value="/sign-out")
    public ResponseCustom<Message> signOut(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "Schemas의 RefreshTokenRequest를 참고해주세요.", required = true) @Valid @RequestBody RefreshTokenReq tokenRefreshRequest
    ) {
        return ResponseCustom.OK(authService.signOut(tokenRefreshRequest));
    }

    @Operation(summary = "문자 전송", description = "입력한 번호로 인증번호를 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문자 전송 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SmsResponseDto.class) ) } ),
            @ApiResponse(responseCode = "400", description = "문자 전송 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping("/send-one")
    public ResponseCustom<SmsResponseDto> sendOne(
            @Parameter(description = "SmsReq Schema를 참고해주세요", required = true) @RequestBody NaverVerifySmsReq naverVerifySmsReq
    ) throws Exception {
        return ResponseCustom.OK(naverSmsClient.send(naverVerifySmsReq.getReceivePhoneNumber()));
    }

    @Operation(summary = "인증 번호 검증", description = "인증번호를 검증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 번호 검증 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SmsResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "인증 번호 검증 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping("/valid-verification-code")
    public ResponseCustom<Message> validVerificationCode(
            @Parameter(description = "ValidVerificationCodeReq Schema를 참고해주세요") @RequestBody ValidVerificationCodeReq validVerificationCodeReq
    ) {
        return ResponseCustom.OK(naverSmsClient.validVerificationCode(validVerificationCodeReq));
    }

    @Operation(summary = "사장 회원가입", description = "사장 회원가입을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장 회원가입 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", description = "사장 회원가입 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value="/shop-owner/sign-up")
    public ResponseCustom<AuthRes> shopOwnerSignUp(
            @Parameter(description = "ShopOwnerSignUpReq Schema를 확인해주세요.", required = true) @RequestBody ShopOwnerSignUpReq shopOwnerSignUpReq
    ) {
        return ResponseCustom.OK(authService.shopOwnerSignUp(shopOwnerSignUpReq));
    }

    @Operation(summary = "사장 로그인", description = "사장 로그인을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장 로그인 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AuthRes.class) ) } ),
            @ApiResponse(responseCode = "400", description = "사장 로그인 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value="/shop-owner/sign-in")
    public ResponseCustom<AuthRes> shopOwnerSignIn(
            @Parameter(description = "ShopOwnerSignInReq Schema를 확인해주세요.", required = true) @RequestBody ShopOwnerSignInReq shopOwnerSignInReq
    ) {
        return ResponseCustom.OK(authService.shopOwnerSignIn(shopOwnerSignInReq));
    }

    @Operation(summary = "사장 로그아웃", description = "사장 로그아웃을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장 로그아웃 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", description = "사장 로그아웃 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value="/shop-owner/sign-out")
    public ResponseCustom<Message> shopOwnerSignOut(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "Schemas의 RefreshTokenRequest를 참고해주세요.", required = true) @Valid @RequestBody RefreshTokenReq tokenRefreshRequest
    ) {
        return ResponseCustom.OK(authService.signOut(tokenRefreshRequest));
    }

    @Operation(summary = "사장 비밀번호 변경", description = "사장 비밀번호 변경을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장 비밀번호 변경 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "사장 비밀번호 변경 실패", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PatchMapping(value = "/shop-owner/change-password")
    public ResponseCustom<Message> shopOwnerChangePassword(
            @Parameter(description = "ShopOwnerChangePasswordReq Schema를 확인해주세요.", required = true) @RequestBody ShopOwnerChangePasswordReq shopOwnerChangePasswordReq
    ) {
        return ResponseCustom.OK(
                authService.shopOwnerChangePassword(shopOwnerChangePasswordReq));
    }

    @Operation(summary = "인증 문자 전송(테스트용 사용 X)", description = "인증 문자 전송(테스트용 사용 X)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문자 전송 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SmsResponseDto.class) ) } ),
            @ApiResponse(responseCode = "400", description = "문자 전송 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping("/send-one/test")
    public ResponseCustom<SmsResponseDto> sendTest(
            @Parameter(description = "SmsReq Schema를 참고해주세요", required = true) @RequestBody NaverVerifySmsReq naverVerifySmsReq
    ) throws Exception {
        return ResponseCustom.OK(naverSmsClient.sendTest(naverVerifySmsReq.getReceivePhoneNumber()));
    }

}

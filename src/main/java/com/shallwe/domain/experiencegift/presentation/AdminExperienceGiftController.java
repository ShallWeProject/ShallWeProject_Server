package com.shallwe.domain.experiencegift.presentation;

import com.shallwe.domain.experiencegift.application.ExperienceGiftServiceImpl;
import com.shallwe.domain.experiencegift.dto.request.AdminExperienceReq;
import com.shallwe.domain.experiencegift.dto.response.ExperienceDetailRes;
import com.shallwe.global.config.security.token.CurrentUser;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.ErrorResponse;
import com.shallwe.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin ExperienceGifts", description = "ExperienceGifts API")
@RequestMapping("/api/v1/admin/experience/gift")
@RestController
@RequiredArgsConstructor
public class AdminExperienceGiftController {
    private final ExperienceGiftServiceImpl experienceGiftService;

    @Operation(summary = "관리자 경험 선물 등록", description = "관리자 경험 선물 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관리자 경험 선물 등록 성공"),
            @ApiResponse(responseCode = "400", description = "관리자 경험 선물 등록 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping("/register")
    public ResponseCustom registerExperienceGift(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @RequestBody @Valid AdminExperienceReq adminExperienceReq
            ) {
        this.experienceGiftService.registerExperienceGift(userPrincipal, adminExperienceReq);
        return ResponseCustom.OK();
    }
}

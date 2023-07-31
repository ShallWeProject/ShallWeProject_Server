package com.shallwe.domain.experience_gift.presentation;

import com.shallwe.domain.experience_gift.application.ExperienceGiftService;
import com.shallwe.domain.experience_gift.application.ExperienceGiftServiceImpl;
import com.shallwe.domain.experience_gift.dto.ExperienceDetailRes;
import com.shallwe.domain.experience_gift.dto.ExperienceRes;
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
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/experience/gift")
@RestController
@RequiredArgsConstructor
public class ExperienceGiftController {
    private final ExperienceGiftServiceImpl experienceGiftService;
    @Operation(summary = "경험 검색 조회", description = "경험 검색을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경험 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExperienceRes.class))}),
            @ApiResponse(responseCode = "400", description = "경험 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping
    public ResponseCustom<List<ExperienceRes>> searchExperience(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @RequestParam String title
    ){
        return ResponseCustom.OK(experienceGiftService.searchExperience(userPrincipal,title));
    }

    @Operation(summary = "경험 상세 조회", description = "경험을 상세 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경험 상세 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExperienceDetailRes.class))}),
            @ApiResponse(responseCode = "400", description = "경험 상세 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/details/{ExperienceGiftId}")
    public ResponseCustom<ExperienceDetailRes> getExperienceDetails(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @PathVariable Long ExperienceGiftId
    ){
        return ResponseCustom.OK(experienceGiftService.getExperienceDetails(userPrincipal,ExperienceGiftId));
    }





}

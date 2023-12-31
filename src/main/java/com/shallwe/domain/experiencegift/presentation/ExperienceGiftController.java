package com.shallwe.domain.experiencegift.presentation;

import com.shallwe.domain.experiencegift.application.ExperienceGiftServiceImpl;
import com.shallwe.domain.experiencegift.dto.response.*;
import com.shallwe.domain.experiencegift.exception.ExperienceGiftNotFoundException;
import com.shallwe.global.config.Constant;
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
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ExperienceGifts", description = "ExperienceGifts API")
@RequestMapping("/api/v1/experience/gift")
@RestController
@RequiredArgsConstructor
public class ExperienceGiftController {

    private final ExperienceGiftServiceImpl experienceGiftService;

    @Operation(summary = "메인 페이지", description = "메인 페이지을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메인 페이지 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExperienceMainRes.class))}),
            @ApiResponse(responseCode = "400", description = "메인 페이지 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping
    public ResponseCustom<ExperienceMainRes> mainPage(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) {
        return ResponseCustom.OK(experienceGiftService.mainPage(userPrincipal));
    }

    @Operation(summary = "인기별 전체 경험선물 조회", description = "인기별 전체 경험선물 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인기별 전체 경험선물 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ExperienceRes.class)))}),
            @ApiResponse(responseCode = "400", description = "인기별 전체 경험선물 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/popular")
    public ResponseCustom<List<ExperienceRes>> getAllPopularGift(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) {
        return ResponseCustom.OK(experienceGiftService.getAllPopularGift(userPrincipal));

    }

    @Operation(summary = "경험 검색 조회", description = "경험 검색을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경험 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ExperienceRes.class)))}),
            @ApiResponse(responseCode = "400", description = "경험 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/search")
    public ResponseCustom<List<ExperienceRes>> searchExperience(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @RequestParam String title
    ) {
        return ResponseCustom.OK(experienceGiftService.searchExperience(userPrincipal, title));
    }

    @Operation(summary = "경험 상세 조회", description = "경험을 상세 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경험 상세 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExperienceDetailRes.class))}),
            @ApiResponse(responseCode = "400", description = "경험 상세 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/details/{experience-gift-id}")
    public ResponseCustom<ExperienceDetailRes> getExperienceDetails(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @PathVariable(name = "experience-gift-id") Long experienceGiftId
    ) {
        return ResponseCustom.OK(experienceGiftService.getExperienceDetails(userPrincipal, experienceGiftId));
    }

    @Operation(summary = "상황별 카테고리 경험선물 조회", description = "상황별 카테고리 경험선물 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상황별 경험선물 카테고리 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ExperienceSttCategoryRes.class)))}),
            @ApiResponse(responseCode = "400", description = "상황별 경험선물 카테고리 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/stt-category/{situation-category-id}")
    public ResponseCustom<List<ExperienceSttCategoryRes>> getSttCategoryGift(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true)
            @CurrentUser UserPrincipal userPrincipal,
            @PathVariable(name = "situation-category-id") Long sttCategoryId,
            @RequestParam(name = "category") String category
    ) {
        if (category.equals(Constant.ExperienceGiftConstant.POPULAR_EXPERIENCE_GIFT)) {
            return ResponseCustom.OK(experienceGiftService.getPopularSttGift(userPrincipal, sttCategoryId));
        } else if (category.equals(Constant.ExperienceGiftConstant.RECOMMEND_EXPERIENCE_GIFT)) {
            return ResponseCustom.OK(experienceGiftService.getPopularSttGift(userPrincipal, sttCategoryId));
        } else if (category.equals(Constant.ExperienceGiftConstant.HIGH_PRICED_ORDER)) {
            return ResponseCustom.OK(experienceGiftService.highSttCategoryPricedGift(userPrincipal, sttCategoryId));
        } else if (category.equals(Constant.ExperienceGiftConstant.LOW_PRICED_ORDER)) {
            return ResponseCustom.OK(experienceGiftService.lowSttCategoryPricedGift(userPrincipal, sttCategoryId));
        } else {
            throw new ExperienceGiftNotFoundException();
        }
    }

    @Operation(summary = "경험카테고리별 경험선물 조회", description = "경험카테고리별 경험선물 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경험카테고리별 경험선물 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ExperienceExpCategoryRes.class)))}),
            @ApiResponse(responseCode = "400", description = "경험카테고리별 경험선물 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/exp-category/{experience-category-id}")
    public ResponseCustom<List<ExperienceExpCategoryRes>> getExpCategoryGift(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @PathVariable(name = "experience-category-id") Long expCategoryId,
            @RequestParam(name = "category") String category
    ) {
        if (category.equals(Constant.ExperienceGiftConstant.POPULAR_EXPERIENCE_GIFT)) {
            return ResponseCustom.OK(experienceGiftService.getPopulaExpGift(userPrincipal, expCategoryId));
        } else if (category.equals(Constant.ExperienceGiftConstant.RECOMMEND_EXPERIENCE_GIFT)) {
            return ResponseCustom.OK(experienceGiftService.getPopulaExpGift(userPrincipal, expCategoryId));
        } else if (category.equals(Constant.ExperienceGiftConstant.HIGH_PRICED_ORDER)) {
            return ResponseCustom.OK(experienceGiftService.highExpCategoryPricedGift(userPrincipal, expCategoryId));
        } else if (category.equals(Constant.ExperienceGiftConstant.LOW_PRICED_ORDER)) {
            return ResponseCustom.OK(experienceGiftService.lowExpCategoryPricedGift(userPrincipal, expCategoryId));
        } else {
            throw new ExperienceGiftNotFoundException();
        }
    }

}

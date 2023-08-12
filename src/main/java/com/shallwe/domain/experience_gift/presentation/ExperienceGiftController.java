package com.shallwe.domain.experience_gift.presentation;

import com.shallwe.domain.experience_gift.application.ExperienceGiftServiceImpl;
import com.shallwe.domain.experience_gift.dto.request.ExperienceReq;
import com.shallwe.domain.experience_gift.dto.response.ExperienceDetailRes;
import com.shallwe.domain.experience_gift.dto.response.ExperienceExpCategoryRes;
import com.shallwe.domain.experience_gift.dto.response.ExperienceRes;
import com.shallwe.domain.experience_gift.dto.response.ExperienceSttCategoryRes;
import com.shallwe.domain.experience_gift.exception.ExperienceGiftNotFoundException;
import com.shallwe.global.config.Constant;
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

    @Operation(summary = "상황별 카테고리 경험선물 조회", description = "상황별 카테고리 경험선물 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상황별 경험선물 카테고리 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExperienceSttCategoryRes.class))}),
            @ApiResponse(responseCode = "400", description = "상황별 경험선물 카테고리 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/stt-category/{SttCategoryId}")
    public ResponseCustom<List<ExperienceSttCategoryRes>> getSttCategoryGift(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true)
            @CurrentUser UserPrincipal userPrincipal,
            @PathVariable Long SttCategoryId,
            @RequestParam(name = "category") String category
            ){
        if (category.equals(Constant.ExperienceGiftConstant.POPULAR_EXPERIENCE_GIFT)) {
            return ResponseCustom.OK(experienceGiftService.getPopularSttGift(userPrincipal, SttCategoryId));
        }else if(category.equals(Constant.ExperienceGiftConstant.RECOMMEND_EXPERIENCE_GIFT)) {
                return ResponseCustom.OK(experienceGiftService.getPopularSttGift(userPrincipal, SttCategoryId));
        } else if(category.equals(Constant.ExperienceGiftConstant.HIGH_PRICED_ORDER)){
            return ResponseCustom.OK(experienceGiftService.highSttCategoryPricedGift(userPrincipal,SttCategoryId));
        } else if (category.equals(Constant.ExperienceGiftConstant.LOW_PRICED_ORDER)) {
            return ResponseCustom.OK(experienceGiftService.lowSttCategoryPricedGift(userPrincipal,SttCategoryId));

        }else{
            throw new ExperienceGiftNotFoundException();
        }
    }

    @Operation(summary = "경험카테고리별 경험선물 조회", description = "경험카테고리별 경험선물 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경험카테고리별 경험선물 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExperienceExpCategoryRes.class))}),
            @ApiResponse(responseCode = "400", description = "경험카테고리별 경험선물 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/exp-category/{ExpCategoryId}")
    public ResponseCustom<List<ExperienceExpCategoryRes>> getExpCategoryGift(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @PathVariable Long ExpCategoryId,
            @RequestParam(name = "category") String category
    ){
        if (category.equals(Constant.ExperienceGiftConstant.POPULAR_EXPERIENCE_GIFT)) {
            return ResponseCustom.OK(experienceGiftService.getPopulaExpGift(userPrincipal, ExpCategoryId));
        }else if(category.equals(Constant.ExperienceGiftConstant.RECOMMEND_EXPERIENCE_GIFT)) {
            return ResponseCustom.OK(experienceGiftService.getPopulaExpGift(userPrincipal, ExpCategoryId));
        } else if(category.equals(Constant.ExperienceGiftConstant.HIGH_PRICED_ORDER)){
            return ResponseCustom.OK(experienceGiftService.highExpCategoryPricedGift(userPrincipal,ExpCategoryId));
        } else if (category.equals(Constant.ExperienceGiftConstant.LOW_PRICED_ORDER)) {
            return ResponseCustom.OK(experienceGiftService.lowExpCategoryPricedGift(userPrincipal,ExpCategoryId));
        }else{
            throw new ExperienceGiftNotFoundException();
        }
    }

    @Operation(summary = "경험 선물 추가", description = "경험 선물을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경험 선물 추가 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExperienceDetailRes.class))}),
            @ApiResponse(responseCode = "400", description = "경험 선물 추가 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping
    public ResponseCustom<ExperienceDetailRes> CreateExpGift(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "예약 요청을 확인해주세요.", required = true) @RequestBody ExperienceReq reservationRequest
    ){
       return ResponseCustom.OK(experienceGiftService.createExperience(userPrincipal,reservationRequest));
        }



}

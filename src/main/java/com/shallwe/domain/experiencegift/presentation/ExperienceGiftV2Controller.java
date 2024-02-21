package com.shallwe.domain.experiencegift.presentation;

import com.shallwe.domain.experiencegift.application.ExperienceGiftServiceImpl;
import com.shallwe.domain.experiencegift.dto.response.ExperienceGiftRes;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "ExperienceGifts V2", description = "ExperienceGifts V2 API")
@RequestMapping("/api/v2/experience-gifts")
@RestController
@RequiredArgsConstructor
public class ExperienceGiftV2Controller {

    private final ExperienceGiftServiceImpl experienceGiftService;

    @Operation(summary = "조건별 경험선물 조회(Paging)", description = "조건별 경험선물 조회합니다.(Paging)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조건별 전체 경험선물 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ExperienceGiftRes.class)))}),
            @ApiResponse(responseCode = "400", description = "조건별 전체 경험선물 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/list")
    public ResponseCustom<Slice<ExperienceGiftRes>> getPagedExperienceGiftByPopular(
            @Parameter(name = "sttCategory", description = "상황 카테고리") @RequestParam(name = "sttCategory", required = false) final String sttCategory,
            @Parameter(name = "expCategory", description = "경험 카테고리") @RequestParam(name = "expCategory", required = false) final String expCategory,
            @Parameter(name = "searchCondition", description = "검색어") @RequestParam(name = "searchCondition", required = false) final String searchCondition,
            @Parameter(name = "sortCondition", description = "인기순, 추천순, 가격높은순, 가격낮은순") @RequestParam(name = "sortCondition", required = false, defaultValue = "popular") final String sortCondition,
            @Parameter(name = "pagingCondition", description = "조회 할 페이지와 페이지 크기를 입력해주세요(sort는 무시해도 됩니다. + Page는 0번부터 시작)") Pageable pageable
    ) {
        return ResponseCustom.OK(experienceGiftService.getPagedExperienceGifts(pageable, sttCategory, searchCondition, expCategory, sortCondition));
    }

}

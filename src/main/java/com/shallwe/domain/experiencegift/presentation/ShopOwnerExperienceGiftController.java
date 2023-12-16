package com.shallwe.domain.experiencegift.presentation;

import com.shallwe.domain.experiencegift.application.ExperienceGiftServiceImpl;
import com.shallwe.domain.experiencegift.dto.request.ShopOwnerExperienceReq;
import com.shallwe.domain.experiencegift.dto.response.ShopOwnerMainRes;
import com.shallwe.domain.experiencegift.dto.response.ShopOwnerExperienceRes;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ShopOwner ExperienceGifts", description = "ShopOwner ExperienceGifts API")
@RequestMapping("/api/v1/experience/gift/shop-owner")
@RestController
@RequiredArgsConstructor
public class ShopOwnerExperienceGiftController {

    private final ExperienceGiftServiceImpl experienceGiftService;

    @Operation(summary = "사장님 경험 선물 등록", description = "사장님 경험 선물 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장님 경험 선물 등록 성공"),
            @ApiResponse(responseCode = "400", description = "사장님 경험 선물 등록 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping("/register")
    public ResponseCustom<Void> registerExperienceGift(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @RequestBody @Valid ShopOwnerExperienceReq shopOwnerExperienceReq
    ) {
        this.experienceGiftService.registerExperienceGift(userPrincipal, shopOwnerExperienceReq);
        return ResponseCustom.OK();
    }

    @Operation(summary = "사장님 메인 페이지", description = "사장님 메인 페이지를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장님 메인 페이지를 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ShopOwnerMainRes.class))}),
            @ApiResponse(responseCode = "400", description = "사장님 메인 페이지를 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/main")
    public ResponseCustom<ShopOwnerMainRes> mainAdminExperienceGift(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) {
        return ResponseCustom.OK(experienceGiftService.mainAdminExperienceGift(userPrincipal));
    }

    @Operation(summary = "사장님 경험 선물 조회", description = "사장님 경험 선물 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장님 경험 선물 조회 성공", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShopOwnerExperienceRes.class)))}),
            @ApiResponse(responseCode = "400", description = "사장님 경험 선물 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("")
    public ResponseCustom<List<ShopOwnerExperienceRes>> getExperienceGift(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) {
        return ResponseCustom.OK(experienceGiftService.getExperienceGift(userPrincipal));
    }

    @Operation(summary = "사장님 경험 선물 수정", description = "사장님 경험 선물 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장님 경험 선물 수정 성공"),
            @ApiResponse(responseCode = "400", description = "사장님 경험 선물 수정 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PutMapping("/{experienceGiftId}")
    public ResponseCustom<Void> modifyExperienceGift(
            @PathVariable Long experienceGiftId,
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @RequestBody ShopOwnerExperienceReq shopOwnerExperienceReq
    ) {
        this.experienceGiftService.modifyExperienceGift(experienceGiftId, userPrincipal, shopOwnerExperienceReq);
        return ResponseCustom.OK();
    }

    @Operation(summary = "사장님 경험 선물 삭제", description = "사장님 경험 선물을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장님 경험 선물 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "사장님 경험 선물 삭제 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PatchMapping("/{experienceGiftId}")
    public ResponseCustom<Void> deleteExperienceGift(
            @PathVariable Long experienceGiftId,
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) {
        experienceGiftService.deleteExperienceGift(experienceGiftId, userPrincipal);
        return ResponseCustom.OK();
    }

}

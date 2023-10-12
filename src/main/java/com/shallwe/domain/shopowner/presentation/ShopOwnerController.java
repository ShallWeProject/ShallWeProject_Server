package com.shallwe.domain.shopowner.presentation;


import com.shallwe.domain.shopowner.application.ShopOwnerServiceImpl;
import com.shallwe.domain.shopowner.dto.ShopOwnerChangePasswordReq;
import com.shallwe.global.config.security.token.CurrentUser;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.ErrorResponse;
import com.shallwe.global.payload.Message;
import com.shallwe.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ShopOwners", description = "ShopOwners API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/shop-owners")
public class ShopOwnerController {

    private final ShopOwnerServiceImpl shopOwnerService;

    @Operation(summary = "사장 비밀번호 변경", description = "사장 비밀번호 변경을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장 비밀번호 변경 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", description = "사장 비밀번호 변경 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PatchMapping(value="/change-password")
    public ResponseCustom<Message> shopOwnerChangePassword(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "ShopOwnerChangePasswordReq Schema를 확인해주세요.", required = true) @RequestBody ShopOwnerChangePasswordReq shopOwnerChangePasswordReq
            ) {
        return ResponseCustom.OK(shopOwnerService.shopOwnerChangePassword(userPrincipal, shopOwnerChangePasswordReq));
    }

}

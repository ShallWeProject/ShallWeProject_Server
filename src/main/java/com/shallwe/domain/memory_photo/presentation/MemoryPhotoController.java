package com.shallwe.domain.memory_photo.presentation;

import com.shallwe.domain.memory_photo.application.MemoryPhotoServiceImpl;
import com.shallwe.domain.memory_photo.dto.MemoryPhotoDetailRes;
import com.shallwe.domain.user.dto.UserDetailRes;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/memory-photo")
public class MemoryPhotoController {

    private final MemoryPhotoServiceImpl memoryPhotoService;

    @Operation(summary = "날짜에 해당하는 MemoryPhoto 조회", description = "날짜에 해당하는 MemoryPhoto 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "MemoryPhoto 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MemoryPhotoDetailRes.class))}),
            @ApiResponse(responseCode = "400", description = "MemoryPhoto 조회 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping("/{date}")
    public ResponseCustom<?> getMemoryPhotoByDate(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "날짜를 입력해주세요.", required = true) @PathVariable LocalDateTime date
    ) {
        return ResponseCustom.OK(memoryPhotoService.getMemoryPhotoByDate(userPrincipal, date));
    }

}

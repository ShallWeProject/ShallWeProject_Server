package com.shallwe.domain.memoryPhoto.presentation;

import com.shallwe.domain.memoryPhoto.application.MemoryPhotoServiceImpl;
import com.shallwe.domain.memoryPhoto.dto.MemoryPhotoDetailRes;
import com.shallwe.domain.memoryPhoto.dto.UploadMemoryPhotoReq;
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
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public ResponseCustom<?> getMemoryPhotoByDate(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "날짜를 입력해주세요.[YYYY-MM-DD HH:mm] 형식입니다.", required = true)
            @RequestParam(value = "date")
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")LocalDateTime date
    ) {
        return ResponseCustom.OK(memoryPhotoService.getMemoryPhotoByDate(userPrincipal, date));
    }

    @Operation(summary = "메모리 포토를 업로드 합니다.", description = "메모리 포토를 업로드 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "MemoryPhoto 업로드 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "400", description = "MemoryPhoto 업로드 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping()
    public ResponseCustom<?> uploadMemoryPhoto(
            @Parameter(description = "AccessToken 을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "UploadMemoryPhotoReq Schema를 확인해주세요.", required = true) @RequestBody UploadMemoryPhotoReq uploadMemoryPhotoReq
    ) {
        return ResponseCustom.OK(memoryPhotoService.uploadMemoryPhoto(userPrincipal, uploadMemoryPhotoReq));
    }

}

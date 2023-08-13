package com.shallwe.domain.file.presentation;

import com.shallwe.domain.file.application.S3Service;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@RestController
@RequestMapping(value = "api/v1/file")
@RequiredArgsConstructor
public class FileController {
    private final S3Service s3Service;

    @Operation(summary = "PresignedURL 생성", description = "파일 업로드를 위한 PresignedURL을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Presigned URl 생성 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDetailRes.class))}),
            @ApiResponse(responseCode = "400", description = "Presigned URL 생성 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping("/generate-presigned-url")
    public ResponseCustom<String> generatedPresignedURL(
            @Parameter(description = "fileName을 입력해주세요", required = true) @RequestParam("fileName") String fileName
    ) {
        String objectKey= "uploads/" + fileName; //S3 대상 경로 지정
        URL presignedURL = s3Service.generatePresignedUrl(objectKey,30);
        return ResponseCustom.OK(presignedURL.toString());
    }


}

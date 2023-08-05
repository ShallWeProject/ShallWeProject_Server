package com.shallwe.domain.file.presentation;

import com.shallwe.domain.file.application.FileUploadService;
import com.shallwe.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/upload")
@RequiredArgsConstructor
public class FileController {
    private final FileUploadService fileUploadService;

    @PostMapping
    public ResponseCustom<String> uploadFile(
            @RequestParam("images") MultipartFile multipartFile)throws IOException {
        return ResponseCustom.OK(fileUploadService.upload(multipartFile));
    }


}

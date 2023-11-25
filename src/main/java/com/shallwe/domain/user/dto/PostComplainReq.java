package com.shallwe.domain.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostComplainReq {

    @Schema(type = "string", example = "마음에 안 들어요.", description = "불편 사항")
    private String complain;

}

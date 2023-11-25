package com.shallwe.domain.experiencegift.dto.request;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.ExperienceGiftImg;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminExperienceReq {
    @Schema(type = "String", description = "지역", maxLength = 30)
    @NotBlank(message = "지역은 필수 입력 값입니다.")
    private String title;

    @Schema(type = "String", description = "경험 카테고리")
    private String expCategory;

    @Schema(type = "String", description = "상황 카테고리")
    private String sttCategory;

    @Schema(type = "String", description = "상품명", maxLength = 100)
    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String subtitle;

    @Schema(type = "String", description = "썸네일")
    private List<String> giftImgKey;

    @Schema(type = "String", description = "상품 설명", maxLength = 256)
    @NotBlank(message = "상품 설명은 필수 입력 값입니다.")
    private String description;

    @Schema(type = "List", description = "상품 상세 작성")
    @NotEmpty(message = "상품 상세 작성은 필수 입력 값입니다.")
    private List<ExplanationReq> explanation;

    @Schema(type = "String", description = "주소", maxLength = 30)
    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String location;

    @Schema(type = "Long", description = "가격", maxLength = 30)
    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Long price;
}

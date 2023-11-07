package com.shallwe.domain.experiencegift.dto.response;

import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminExperienceRes {
    @Schema(type = "Long", description = "경험선물 Id", example = "1")
    private Long experienceGiftId;
    @Schema(type = "String", description = "상품명", maxLength = 100)
    private String subtitle;
    @Schema(type = "String", description = "지역", maxLength = 30)
    private String title;

    public static AdminExperienceRes toDto(ExperienceGift experienceGift){
        return AdminExperienceRes.builder()
                .experienceGiftId(experienceGift.getExperienceGiftId())
                .subtitle(experienceGift.getSubtitle().getTitle())
                .title(experienceGift.getTitle())
                .build();
    }

}

package com.shallwe.domain.experiencegift.dto.response;

import com.shallwe.domain.experiencegift.domain.ExperienceCategory;
import com.shallwe.domain.experiencegift.domain.ExperienceGift;
import com.shallwe.domain.experiencegift.domain.Explanation;
import com.shallwe.domain.experiencegift.dto.request.ExplanationReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ShopOwnerExperienceDetailsRes {

    private String subtitle;

    private String expCategory;

    private String title;

    private List<String> giftImgKey;

    private String description;

    private List<ExplanationRes> explanation;

    private String location;

    private Long price;

    private String note;

    private Long experienceGiftId;

    public static ShopOwnerExperienceDetailsRes toDto(ExperienceGift experienceGift, ExperienceCategory experienceCategory, List<Explanation> explanations, List<String> giftImgUrl){
        return ShopOwnerExperienceDetailsRes.builder()
                .experienceGiftId(experienceGift.getId())
                .subtitle(experienceGift.getSubtitle().getTitle())
                .expCategory(experienceCategory.getExpCategory())
                .title(experienceGift.getTitle())
                .giftImgKey(giftImgUrl)
                .description(experienceGift.getDescription())
                .explanation(explanations.stream().map(m -> ExplanationRes.toDto(m.getStage(),m.getDescription(), m.getExplanationKey())).collect(Collectors.toList()))
                .location(experienceGift.getLocation())
                .price(experienceGift.getPrice())
                .note(experienceGift.getNote())
                .experienceGiftId(experienceGift.getId())
                .build();

    }
}

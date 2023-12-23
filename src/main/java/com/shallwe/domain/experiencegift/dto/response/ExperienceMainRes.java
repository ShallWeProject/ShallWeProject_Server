package com.shallwe.domain.experiencegift.dto.response;
import com.shallwe.domain.experiencegift.domain.ExperienceCategory;
import com.shallwe.domain.experiencegift.domain.SituationCategory;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceMainRes {

    private List<ExpCategoryRes> expCategories;
    private List<SttCategoryRes> sttCategoryRes;

    public static ExperienceMainRes toDto(List<ExperienceCategory> expCategories, List<SituationCategory> sttCategories){
        ExperienceMainRes experienceMainRes=new ExperienceMainRes();
        experienceMainRes.expCategories = expCategories.stream().map(ExpCategoryRes::toDto).collect(Collectors.toList());
        experienceMainRes.sttCategoryRes = sttCategories.stream().map(SttCategoryRes::toDto).collect(Collectors.toList());
        return experienceMainRes;
    }

}

package com.shallwe.domain.experiencegift.dto.response;
import com.shallwe.domain.experiencegift.domain.ExpCategory;
import com.shallwe.domain.experiencegift.domain.SttCategory;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExperienceMainRes {
    private List<ExpCategoryRes> expCategories;
    private List<SttCategoryRes> sttCategoryRes;


    public static ExperienceMainRes toDto(List<ExpCategory> expCategories, List<SttCategory> sttCategories){
        ExperienceMainRes experienceMainRes=new ExperienceMainRes();
        experienceMainRes.expCategories = expCategories.stream().map(ExpCategoryRes::toDto).collect(Collectors.toList());
        experienceMainRes.sttCategoryRes = sttCategories.stream().map(SttCategoryRes::toDto).collect(Collectors.toList());
        return experienceMainRes;
    }



}

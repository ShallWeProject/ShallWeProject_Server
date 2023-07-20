package com.shallwe.domain.user.dto;

import com.shallwe.domain.user.domain.Gender;
import com.shallwe.domain.user.domain.Provider;
import com.shallwe.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRes {

    private Long id;

    private String name;

    private String birthDay;

    private Integer age;

    private String email;

    private String profileImgUrl;

    private Gender gender;

    @Builder
    public UserRes(Long id, String name, String birthDay, Integer age, String email, String profileImgUrl, Gender gender){
        this.id=id;
        this.name=name;
        this.birthDay=birthDay;
        this.age=age;
        this.email=email;
        this.profileImgUrl=profileImgUrl;
        this.gender=gender;

    }

    public static UserRes toDto(User user){
        UserResBuilder builder=UserRes.builder()
                .id(user.getId())
                .name(user.getName())
                .birthDay(user.getBirthDay())
                .age(user.getAge())
                .email(user.getEmail())
                .profileImgUrl(user.getProfileImgUrl())
                .gender(user.getGender());
        return builder.build();

    }

}

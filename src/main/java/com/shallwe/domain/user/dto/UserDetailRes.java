package com.shallwe.domain.user.dto;

import com.shallwe.domain.user.domain.Gender;
import com.shallwe.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDetailRes {

    private Long id;

    private String name;

    private String birthDay;

    private Integer age;

    private String email;

    private String profileImgUrl;

    private Gender gender;

    @Builder
    public UserDetailRes(Long id, String name, String birthDay, Integer age, String email, String profileImgUrl, Gender gender){
        this.id=id;
        this.name=name;
        this.birthDay=birthDay;
        this.age=age;
        this.email=email;
        this.profileImgUrl=profileImgUrl;
        this.gender=gender;
    }

    public static UserDetailRes toDto(User user){
        UserDetailResBuilder builder= UserDetailRes.builder()
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

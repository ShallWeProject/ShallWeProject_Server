package com.shallwe.domain.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.shallwe.domain.common.Status;
import com.shallwe.domain.user.domain.Gender;
import com.shallwe.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class UserDetailRes {

    private Long id;
    private String name;
    private String birthDay;
    private Integer age;
    private String phoneNumber;
    private String email;
    private String profileImgUrl;
    private Gender gender;
    private Status status;

    public static UserDetailRes toDto(User user) {
        return UserDetailRes.builder()
                .id(user.getId())
                .name(user.getName())
                .birthDay(user.getBirthDay())
                .age(user.getAge())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .profileImgUrl(user.getProfileImgUrl())
                .gender(user.getGender())
                .status(user.getStatus())
                .build();
    }

    @Builder
    @QueryProjection
    public UserDetailRes(Long id, String name, String birthDay, Integer age, String phoneNumber, String email, String profileImgUrl, Gender gender, Status status) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.gender = gender;
        this.status = status;
    }

}

package com.shallwe.domain.user.dto;

import com.shallwe.domain.common.Status;
import com.shallwe.domain.user.domain.Gender;
import com.shallwe.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
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

}

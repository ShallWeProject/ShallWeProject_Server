package com.shallwe.domain.user.dto;

import com.shallwe.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDeleteRes {

    private String message;

    @Builder
    public UserDeleteRes(String message){
        this.message=message;
    }

    public static UserDeleteRes toDto(){
        UserDeleteResBuilder builder=UserDeleteRes.builder()
                .message("회원 탈퇴하셨습니다.");

        return builder.build();
    }

}

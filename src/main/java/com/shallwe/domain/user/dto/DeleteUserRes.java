package com.shallwe.domain.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DeleteUserRes {

    private String message;

    @Builder
    public DeleteUserRes(String message){
        this.message=message;
    }

    public static DeleteUserRes toDto(){
        DeleteUserResBuilder builder= DeleteUserRes.builder()
                .message("회원 탈퇴하셨습니다.");

        return builder.build();
    }

}

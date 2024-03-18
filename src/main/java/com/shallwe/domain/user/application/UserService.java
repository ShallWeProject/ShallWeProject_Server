package com.shallwe.domain.user.application;

import com.shallwe.domain.user.dto.*;
import com.shallwe.global.config.security.token.UserPrincipal;
import com.shallwe.global.payload.Message;

import java.util.List;

public interface UserService {

    UserDetailRes getCurrentUser(UserPrincipal userPrincipal);
    void inactiveCurrentUser(UserPrincipal userPrincipal, PostComplainReq postComplainReq);
    void signUpCurrentUser(UserPrincipal userPrincipal, SignUpUserReq signUpUserReq);
    List<SendGiftDetailRes> findSendGiftsByUser(UserPrincipal userPrincipal);
    List<ReceiveGiftDetailRes> findReceiveGiftsByUser(UserPrincipal userPrincipal);

}

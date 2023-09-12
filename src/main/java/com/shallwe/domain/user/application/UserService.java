package com.shallwe.domain.user.application;

import com.shallwe.domain.user.dto.*;
import com.shallwe.global.config.security.token.UserPrincipal;

import java.util.List;

public interface UserService {

    UserDetailRes getCurrentUser(UserPrincipal userPrincipal);
    DeleteUserRes deleteCurrentUser(UserPrincipal userPrincipal);
    SignUpUserRes signUpCurrentUser(UserPrincipal userPrincipal, SignUpUserReq signUpUserReq);
    List<SendAndReceiveGiftDetailRes> findSendGiftsByUser(UserPrincipal userPrincipal);
    List<SendAndReceiveGiftDetailRes> findReceiveGiftsByUser(UserPrincipal userPrincipal);

}

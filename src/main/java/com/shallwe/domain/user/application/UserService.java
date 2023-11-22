package com.shallwe.domain.user.application;

import com.shallwe.domain.user.dto.*;
import com.shallwe.global.config.security.token.UserPrincipal;

import java.util.List;

public interface UserService {

    UserDetailRes getCurrentUser(UserPrincipal userPrincipal);
    DeleteUserRes inactiveCurrentUser(UserPrincipal userPrincipal);
    SignUpUserRes signUpCurrentUser(UserPrincipal userPrincipal, SignUpUserReq signUpUserReq);
    List<SendGiftDetailRes> findSendGiftsByUser(UserPrincipal userPrincipal);
    List<ReceiveGiftDetailRes> findReceiveGiftsByUser(UserPrincipal userPrincipal);

}

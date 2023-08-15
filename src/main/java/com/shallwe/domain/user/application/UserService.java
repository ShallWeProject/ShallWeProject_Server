package com.shallwe.domain.user.application;

import com.shallwe.domain.user.dto.*;
import com.shallwe.global.config.security.token.UserPrincipal;

public interface UserService {

    UserDetailRes getCurrentUser(UserPrincipal userPrincipal);
    DeleteUserRes deleteCurrentUser(UserPrincipal userPrincipal);
    SignUpUserRes signUpCurrentUser(UserPrincipal userPrincipal, SignUpUserReq signUpUserReq);
    SendAndReceiveGiftListRes findSendGiftsByUser(UserPrincipal userPrincipal);
    SendAndReceiveGiftListRes findReceiveGitsByUser(UserPrincipal userPrincipal);

}

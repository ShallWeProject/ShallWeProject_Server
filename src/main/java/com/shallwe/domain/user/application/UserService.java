package com.shallwe.domain.user.application;

import com.shallwe.domain.user.dto.DeleteUserRes;
import com.shallwe.domain.user.dto.SignUpUserReq;
import com.shallwe.domain.user.dto.SignUpUserRes;
import com.shallwe.domain.user.dto.UserDetailRes;
import com.shallwe.global.config.security.token.UserPrincipal;

public interface UserService {

    UserDetailRes getCurrentUser(UserPrincipal userPrincipal);
    DeleteUserRes deleteCurrentUser(UserPrincipal userPrincipal);
    SignUpUserRes signUpCurrentUser(UserPrincipal userPrincipal, SignUpUserReq signUpUserReq);

}

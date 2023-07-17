package com.shallwe.domain.user.application;

import com.shallwe.domain.user.dto.UserDeleteRes;
import com.shallwe.domain.user.dto.UserRes;
import com.shallwe.global.config.security.token.UserPrincipal;

public interface UserService {
    UserRes getCurrentUser(UserPrincipal userPrincipal);
    UserDeleteRes deleteCurrentUser(UserPrincipal userPrincipal);
}

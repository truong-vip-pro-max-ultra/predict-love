package com.truongjae.predictlove.service;

import com.truongjae.predictlove.dto.req.ChangePasswordRequest;
import com.truongjae.predictlove.dto.req.UserRequest;
import com.truongjae.predictlove.dto.req.UserSaveRequest;
import com.truongjae.predictlove.dto.resp.LoginToken;
import com.truongjae.predictlove.dto.resp.UserResponse;
import com.truongjae.predictlove.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void register(UserSaveRequest userSaveRequest);
    LoginToken login(UserRequest userRequest);
    User findOneByUsername(String username);
    UserResponse getUserInfo();

    void changePassword(ChangePasswordRequest changePasswordRequest);
}

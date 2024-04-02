package com.truongjae.predictlove.service.impl;

import com.truongjae.predictlove.dto.Profile;
import com.truongjae.predictlove.dto.req.ChangePasswordRequest;
import com.truongjae.predictlove.dto.req.UserRequest;
import com.truongjae.predictlove.dto.req.UserSaveRequest;
import com.truongjae.predictlove.dto.resp.LoginToken;
import com.truongjae.predictlove.dto.resp.UserResponse;
import com.truongjae.predictlove.entity.User;
import com.truongjae.predictlove.exception.BadRequestException;
import com.truongjae.predictlove.exception.OKException;
import com.truongjae.predictlove.exception.ObjectNotFoundException;
import com.truongjae.predictlove.repoitory.UserRepository;
import com.truongjae.predictlove.security.AuthUtils;
import com.truongjae.predictlove.service.UserService;
import com.truongjae.predictlove.utils.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Profile profile;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void register(UserSaveRequest userSaveRequest) {
        System.out.println(userSaveRequest.getPassword());
        User user = userRepository.findOneByUsername(userSaveRequest.getUsername());
        if(user==null){
            User newUser = new User();
            newUser.setUsername(userSaveRequest.getUsername());
            newUser.setPassword(passwordEncoder.encode(userSaveRequest.getPassword()));
            newUser.setAccountBalance(0L);
            userRepository.save(newUser);
            throw new OKException(MessageResponse.REGISTER_SUCCESS);
        }
        throw new BadRequestException(MessageResponse.REGISTER_FAIL);
    }

    @Override
    public LoginToken login(UserRequest userRequest) {
        User user = userRepository.findOneByUsernameAndPassword(userRequest.getUsername(),userRequest.getPassword());
        if(user!=null){
            LoginToken loginToken = new LoginToken();
            String token = AuthUtils.generateToken(user);
            loginToken.setToken(token);
            return loginToken;
        }
        throw new ObjectNotFoundException(MessageResponse.LOGIN_FAIL);
    }

    @Override
    public User findOneByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public UserResponse getUserInfo() {
        User user = userRepository.findOneByUsername(profile.getUsername());
        if(user!=null){
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setAccountBalance(user.getAccountBalance());
            return userResponse;
        }
        throw new BadRequestException(MessageResponse.LOGIN_FAIL);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())){
            throw new BadRequestException(MessageResponse.NEW_PASSWORD_AND_CONFIRM_OLD_PASSWORD_IS_NOT_EQUALS);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(profile.getUsername(), changePasswordRequest.getOldPassword()));
        }
        catch (Exception e){
            throw new BadRequestException(MessageResponse.OLD_PASSWORD_IS_NOT_CORRECT);
        }
        User user = userRepository.findOneByUsername(profile.getUsername());
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        throw new OKException(MessageResponse.CHANGE_PASSWORD_SUCCESSFULLY);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }
}

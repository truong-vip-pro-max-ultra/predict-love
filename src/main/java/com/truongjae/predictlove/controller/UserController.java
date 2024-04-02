package com.truongjae.predictlove.controller;

import com.truongjae.predictlove.dto.req.ChangePasswordRequest;
import com.truongjae.predictlove.dto.req.UserRequest;
import com.truongjae.predictlove.dto.req.UserSaveRequest;
import com.truongjae.predictlove.dto.resp.UserResponse;
import com.truongjae.predictlove.security.JwtTokenUtil;
import com.truongjae.predictlove.security.jwt.JwtResponse;
import com.truongjae.predictlove.service.UserService;
import com.truongjae.predictlove.utils.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.truongjae.predictlove.exception.ObjectNotFoundException;
import com.truongjae.predictlove.exception.UnauthorizedException;
import com.truongjae.predictlove.entity.User;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/auth/register")
    public void register(@Valid @RequestBody UserSaveRequest userSaveRequest){
        userService.register(userSaveRequest);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest userRequest){
        String username = authenticate(userRequest.getUsername(), userRequest.getPassword());

        final UserDetails userDetails = userService.loadUserByUsername(username);

        final String token = jwtTokenUtil.generateToken(userDetails);

        // return token
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/user")
    public UserResponse getUserInfo(){
        return userService.getUserInfo();
    }

    private String authenticate(String username, String password){
        User user = userService.findOneByUsername(username);
        if(user==null) throw new ObjectNotFoundException(MessageResponse.LOGIN_FAIL);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException |BadCredentialsException e) {
            throw new UnauthorizedException(MessageResponse.LOGIN_FAIL);
        }
        return username;
    }
    @PostMapping("/change-password")
    public void changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest){
        userService.changePassword(changePasswordRequest);
    }
}

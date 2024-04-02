package com.truongjae.predictlove.security.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class JwtRequest {

    @NotEmpty(message = "Tên tài khoản không được để trống")
    @NotBlank(message = "Tên tài khoản không được để trống")
    private String username;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
}

package com.truongjae.predictlove.dto.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class UserRequest {
    @NotEmpty(message = "Tên tài khoản không được để trống")
    @NotBlank(message = "Tên tài khoản không được để trống")
    private String username;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
}

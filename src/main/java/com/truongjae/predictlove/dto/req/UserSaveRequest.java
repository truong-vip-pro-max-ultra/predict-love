package com.truongjae.predictlove.dto.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserSaveRequest {
    @NotEmpty(message = "Tên tài khoản không được để trống")
    @NotBlank(message = "Tên tài khoản không được để trống")
    @Length(min = 5, max = 15, message = "Tên tài khoản phải có độ dài từ 5 đến 15")
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "Tên tài khoản không được chứa kí tự cấm")
    private String username;
    @NotEmpty(message = "Mật khẩu không được để trống")
    @NotBlank(message = "Mật khẩu không được để trống")
    @Length(min = 5, max = 255, message = "Mật khẩu phải có độ dài từ 5 đến 255")
    private String password;
}

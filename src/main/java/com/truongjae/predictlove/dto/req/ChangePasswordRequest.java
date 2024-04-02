package com.truongjae.predictlove.dto.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class ChangePasswordRequest {
    @NotEmpty(message = "Mật khẩu cũ không được để trống")
    @NotBlank(message = "Mật khẩu cũ không được để trống")
    private String oldPassword;
    @NotEmpty(message = "Mật khẩu mới không được để trống")
    @NotBlank(message = "Mật khẩu mới không được để trống")
    @Length(min = 5, max = 255, message = "Mật khẩu mới phải có độ dài từ 5 đến 255")
    private String newPassword;

    private String confirmNewPassword;
}

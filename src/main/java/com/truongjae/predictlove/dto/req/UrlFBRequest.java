package com.truongjae.predictlove.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class UrlFBRequest {
    @NotEmpty(message = "Link Facebook không được để trống")
    @NotBlank(message = "Link Facebook không được để trống")
    private String urlFB;
}

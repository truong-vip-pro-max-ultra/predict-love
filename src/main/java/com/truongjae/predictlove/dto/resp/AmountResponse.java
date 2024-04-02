package com.truongjae.predictlove.dto.resp;

import lombok.Data;

import java.util.Date;

@Data
public class AmountResponse {
    private Date createdDate;
    private Long amount;
    private String username;
}

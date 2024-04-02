package com.truongjae.predictlove.controller;

import com.truongjae.predictlove.dto.resp.AmountResponse;
import com.truongjae.predictlove.service.AmountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AmountController {
    private final AmountService amountService;

    @PostMapping("/amounts")
    public List<AmountResponse> getListAmount(){
        return amountService.getListAmount();
    }
}

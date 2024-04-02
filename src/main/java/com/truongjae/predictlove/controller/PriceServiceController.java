package com.truongjae.predictlove.controller;

import com.truongjae.predictlove.entity.PriceService;
import com.truongjae.predictlove.exception.BadRequestException;
import com.truongjae.predictlove.service.PriceServiceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PriceServiceController {
    private final PriceServiceService priceServiceService;

    @PostMapping("/price")
    public PriceService getPriceService(){
        return priceServiceService.getPriceService();
    }
}

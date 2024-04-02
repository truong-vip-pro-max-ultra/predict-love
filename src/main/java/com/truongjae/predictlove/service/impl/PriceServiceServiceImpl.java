package com.truongjae.predictlove.service.impl;

import com.truongjae.predictlove.entity.PriceService;
import com.truongjae.predictlove.repoitory.PriceServiceRepository;
import com.truongjae.predictlove.service.PriceServiceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PriceServiceServiceImpl implements PriceServiceService {
    private final PriceServiceRepository priceServiceRepository;

    @Override
    public PriceService getPriceService() {
        return priceServiceRepository.findAll().get(0);
    }
}

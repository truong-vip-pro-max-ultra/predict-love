package com.truongjae.predictlove.service.impl;

import com.truongjae.predictlove.dto.resp.AmountResponse;
import com.truongjae.predictlove.entity.Amount;
import com.truongjae.predictlove.repoitory.AmountRepository;
import com.truongjae.predictlove.service.AmountService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AmountServiceImpl implements AmountService {
    private final AmountRepository amountRepository;
    @Override
    public List<AmountResponse> getListAmount() {
        List<Amount> amountList = amountRepository.findAll(Sort.by(Sort.Direction.DESC,"createdDate"));
        List<AmountResponse> amountResponseList = new ArrayList<>();
        amountList.stream().forEach(
                amount -> {
                    AmountResponse amountResponse = new AmountResponse();
                    amountResponse.setUsername(amount.getUser().getUsername());
                    amountResponse.setAmount(amount.getAmount());
                    amountResponse.setCreatedDate(amount.getCreatedDate());
                    amountResponseList.add(amountResponse);
                }
        );
        return amountResponseList;
    }
}

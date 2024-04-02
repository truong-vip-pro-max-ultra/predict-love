package com.truongjae.predictlove.controller;

import com.truongjae.predictlove.dto.req.UrlFBRequest;
import com.truongjae.predictlove.dto.resp.FriendResponse;
import com.truongjae.predictlove.service.FBApiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class FBApiController {
    private FBApiService fbApiService;
    @PostMapping("/predict-love")
    public FriendResponse getPredictLove(@Valid @RequestBody UrlFBRequest urlFBRequest){
        return fbApiService.getPredictLove(urlFBRequest.getUrlFB());
    }
}

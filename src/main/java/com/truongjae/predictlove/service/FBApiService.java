package com.truongjae.predictlove.service;

import com.truongjae.predictlove.dto.resp.FriendResponse;

public interface FBApiService {
    FriendResponse getPredictLove(String urlFB);
    String getFb_dtsg(String cookie);
    String getAccessTokenNoFullAccess(String cookie);
}

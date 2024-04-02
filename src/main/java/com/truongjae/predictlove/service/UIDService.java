package com.truongjae.predictlove.service;

import com.truongjae.predictlove.enumtype.EntityType;

public interface UIDService {
    String getUIDFromCookie(String cookie);
    String getPostIdFromUrl(String link,String cookie);
    String getUidFromUrl(EntityType entityType, String link, String cookie);
    String getFullNameFromUrl(String link,String cookie);
}

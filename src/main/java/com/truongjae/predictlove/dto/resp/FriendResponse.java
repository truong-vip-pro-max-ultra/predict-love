package com.truongjae.predictlove.dto.resp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendResponse {
    private String myId;
    private String myFullName;
    private String myUrlAvatar;
    private String friendId;
    private String friendFullName;
    private String friendUrlAvatar;
}

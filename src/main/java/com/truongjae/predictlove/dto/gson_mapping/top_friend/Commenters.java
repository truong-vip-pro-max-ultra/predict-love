package com.truongjae.predictlove.dto.gson_mapping.top_friend;

import lombok.Data;

import java.util.List;

@Data
public class Commenters {
    private List<CommentersNodes> nodes;
}

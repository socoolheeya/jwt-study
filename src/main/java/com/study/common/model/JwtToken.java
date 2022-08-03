package com.study.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtToken {

    private String userId;
    private String accessTokenKey;
    private String refreshTokenKey;
}

package com.study.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class JwtToken {

    private String userId;
    private String accessTokenKey;
    private String refreshTokenKey;
}

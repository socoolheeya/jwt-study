package com.study.security;

public interface JwtProperties {

    final String SECRET_KEY = "1234";
    final int ACCESS_EXPIRATION_TIME = 60 * 60; // 1시간
    final int REFRESH_EXPIRATION_TIME = 60 * 60 * 24 * 10; // 10일

}

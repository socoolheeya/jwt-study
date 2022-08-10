package com.study.security;

public interface JwtProperties {

    final String SECRET_KEY = "12BojaUitNwzUOb7aJYbCTT5EcfQ/zN0OMq/Rgh4m6lM5wtmAdfgiiee8IhxbEsZE7mEUT/qTFV9QE0FmNTy8Q==";
    final int ACCESS_EXPIRATION_TIME = 60 * 60 * 24 * 2 * 10000; // 1시간
    final int REFRESH_EXPIRATION_TIME = 60 * 60 * 24 * 10; // 10일

}

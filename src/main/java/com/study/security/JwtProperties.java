package com.study.security;

public interface JwtProperties {

    final String SECRET_KEY = "12BojaUitNwzUOb7aJYbCTT5EcfQ/zN0OMq/Rgh4m6lM5wtmAdfgiiee8IhxbEsZE7mEUT/qTFV9QE0FmNTy8Q==";
    final int ACCESS_EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 2시간
    final int REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 10; // 10일

}

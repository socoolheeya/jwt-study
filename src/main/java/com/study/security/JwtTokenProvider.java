package com.study.security;

import com.study.common.model.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private Key getSignKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }


    public JwtToken createToken(Authentication authentication) {

        String roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Claims claims = Jwts.claims()
                .setSubject(authentication.getName());
        claims.put("roles", roles);
        claims.put("userId", authentication.getName());

        Date now = new Date();
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + JwtProperties.ACCESS_EXPIRATION_TIME))
                .signWith(this.getSignKey(JwtProperties.SECRET_KEY), SignatureAlgorithm.HS512)
                .compact()
                ;

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + JwtProperties.REFRESH_EXPIRATION_TIME))
                .signWith(this.getSignKey(JwtProperties.SECRET_KEY), SignatureAlgorithm.HS512)
                .compact()
                ;

        return JwtToken.builder()
                .accessTokenKey(accessToken)
                .refreshTokenKey(refreshToken)
                .userId(authentication.getName())
                .build();
    }
}

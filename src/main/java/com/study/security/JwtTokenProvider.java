package com.study.security;

import com.study.common.model.JwtToken;
import com.study.user.model.User;
import com.study.user.repository.UserRepository;
import com.study.user.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JwtProperties.SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }


    public JwtToken createToken(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(user)) {
            return null;
        }

        String userId = String.valueOf(user.getUserId());

        String roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Claims claims = Jwts.claims()
                .setSubject(userId);
        claims.put("roles", roles);
        claims.put("email", email);

        Date now = new Date();
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + JwtProperties.ACCESS_EXPIRATION_TIME))
                .signWith(this.getSignKey(), SignatureAlgorithm.HS512)
                .compact()
                ;

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + JwtProperties.REFRESH_EXPIRATION_TIME))
                .signWith(this.getSignKey(), SignatureAlgorithm.HS512)
                .compact()
                ;

        JwtToken token = new JwtToken();
        token.setAccessTokenKey(accessToken);
        token.setRefreshTokenKey(refreshToken);
        token.setUserId(authentication.getName());

        return token;
    }

    public boolean validationJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(this.getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("invalid JWT signature: {}", e.getMessage());
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            log.error("invalid JWT token: {}", e.getMessage());
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            log.error("JWT Token is expired: {}", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            log.error("JWT Token is unsupported: {}", e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public String parseJwtToken(String token) {
        log.info("parseJwtToken token : {}", token);
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return this.extractJwtToken(token);
        }
        return null;
    }

    public String extractJwtToken(String token) {
        log.info("extractJwtToken token : {}", token);
        return token.substring(7, token.length());
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(String token) {
        String str = (String) this.getClaims(token).get("roles");
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(str, ",");
        while (tokenizer.hasMoreElements()) {
            tokens.add(tokenizer.nextToken());
        }

        userDetailsService.setRoles(tokens);
        UserDetails userDetails = userDetailsService.loadUserByUsername((String)this.getClaims(token).get("email"));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}

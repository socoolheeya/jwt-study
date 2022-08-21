package com.study.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("###### JwtTokenFilter request URI : " + request.getRequestURI());
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            log.info("#### JwtTokenFilter doFilterInternal header : {}", header);
            //jwtTokenProvider.extractJwtToken(header);
            String jwtToken = jwtTokenProvider.parseJwtToken(header);
            log.info("#### JwtTokenFilter doFilterInternal jwtToken : {}", jwtToken);
            if(StringUtils.hasText(jwtToken) && jwtTokenProvider.validationJwtToken(jwtToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("cannot set user authentication: {} ", e.getMessage());
        }
    }
}

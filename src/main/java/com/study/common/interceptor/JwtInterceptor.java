package com.study.common.interceptor;

import com.study.main.model.LoginRequest;
import com.study.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

     private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Authorization");
        log.info("#### preHandle token : {}", token);
        if(!StringUtils.hasText(token)) {
            return true;
        }

        token = jwtTokenProvider.extractJwtToken(token);
        Claims claims = jwtTokenProvider.getClaims(token);
        log.info("#### preHandle claims : {}", claims);
        log.info("#### preHandle handler : {}", handler);

        HandlerMethod method = (HandlerMethod) handler;
        method.getMethod().getParameterTypes();

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

}

package com.study.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends UsernamePasswordAuthenticationFilter {

    private final CustomAuthenticationManager customAuthenticationManager;

    public JwtFilter(CustomAuthenticationManager customAuthenticationManager) {
        this.customAuthenticationManager = customAuthenticationManager;
        super.setAuthenticationManager(customAuthenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            User user = mapper.readValue(request.getInputStream(), User.class);

            //log.debug("JwtFilter email : {}", user.getEmail());
            //log.debug("JwtFilter password : {}", user.getPassword());

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            Authentication authentication = customAuthenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

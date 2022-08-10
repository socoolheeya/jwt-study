package com.study.common.service;

import com.study.common.model.JwtToken;
import com.study.common.repository.JwtTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtTokenRepository jwtTokenRepository;

    public JwtToken create(JwtToken jwtToken) {
        JwtToken token = jwtTokenRepository.findByUserId(jwtToken.getUserId());
        if(!ObjectUtils.isEmpty(token)) {
            jwtTokenRepository.deleteById(token.getId());
        }

        token = jwtTokenRepository.save(jwtToken);

        return token;
    }




}

package com.study.common.service;

import com.study.common.model.JwtToken;
import com.study.common.repository.JwtTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtTokenService {

    private final JwtTokenRepository jwtTokenRepository;

    @Transactional
    public JwtToken create(JwtToken jwtToken) {
        JwtToken token = jwtTokenRepository.findByUserId(jwtToken.getUserId());
        if(!ObjectUtils.isEmpty(token)) {
            jwtTokenRepository.deleteById(token.getId());
        }

        token = jwtTokenRepository.save(jwtToken);

        return token;
    }

    @Transactional
    public long delete(String userId) {
        return jwtTokenRepository.deleteByUserId(userId);
    }

}

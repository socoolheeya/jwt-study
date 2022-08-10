package com.study.security;

import com.study.common.model.JwtToken;
import com.study.common.util.ShaUtil;
import com.study.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JwtTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("jwt 토큰 생성 테스트")
    public void createToken() {

        String email = "lwh0102@gmail.com";
        String password = "1234";

        Authentication authentication = userService.authenticate(email, password);
        JwtToken token = jwtTokenProvider.createToken(authentication);

        System.out.println("token : " + token.toString());
    }

    @Test
    @DisplayName("토큰 시크릿 키 생성 테스트")
    public void shaTest() throws NoSuchAlgorithmException {
        String code = ShaUtil.shaAndBase64("1234", ShaUtil.SHA3_512);
        System.out.println("###code : " + code);
    }
}

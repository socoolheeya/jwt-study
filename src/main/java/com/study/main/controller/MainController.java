package com.study.main.controller;

import com.study.common.model.JwtToken;
import com.study.common.service.JwtTokenService;
import com.study.main.model.LoginRequest;
import com.study.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/")
    public ResponseEntity<?> main() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("메인 페이지 입니다");
    }

    @PostMapping(path = "/auth/sign-in")
    public ResponseEntity<?> signIn(@Validated @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLoginAccount(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        JwtToken token = jwtTokenService.create(jwtTokenProvider.createToken(authentication));

        if(!ObjectUtils.isEmpty(token)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(token);
        }

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("로그인 권한이 없습니다");
    }

    @DeleteMapping(path = "/auth/sign-out")
    public ResponseEntity<?> signOut() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
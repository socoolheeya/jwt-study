package com.study.user.controller;

import com.study.common.model.JwtToken;
import com.study.security.JwtTokenProvider;
import com.study.user.model.User;
import com.study.user.model.http.RequestUser;
import com.study.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable("userId") long userId) {
        User user = userService.userGetUserById(userId);

        if(user != null) {
            log.debug("getUser email : {}", user.getEmail());
            log.debug("getUser userId : {}", userId);
            log.debug("getUser name : {}", user.getName());

            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("권한이 없습니다");
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody RequestUser requestUser) {
        userService.createUser(requestUser);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable long userId,
                                        @RequestBody RequestUser requestUser) {
        User user = userService.updateUser(requestUser);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody RequestUser requestUser) {
        Authentication authentication = userService.authenticate(requestUser.getEmail(), requestUser.getPassword());
        JwtToken token = jwtTokenProvider.createToken(authentication);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}

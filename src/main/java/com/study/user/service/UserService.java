package com.study.user.service;

import com.study.security.CustomAuthenticationManager;
import com.study.user.model.User;
import com.study.user.model.http.RequestUser;
import com.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final CustomAuthenticationManager authenticationManager;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User userGetUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다"));
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Transactional
    public User createUser(RequestUser requestUser) {
        return userRepository.save(
                User.builder()
                        .name(requestUser.getName())
                        .password(requestUser.getPassword())
                        .email(requestUser.getEmail())
                        .build()
        );
    }

    @Transactional
    public User updateUser(RequestUser requestUser) {
        return this.createUser(requestUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Authentication authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}

package com.study.security;

import com.study.user.model.User;
import com.study.user.model.UserRole;
import com.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        log.info("CustomAuthenticationManager email : {}", authentication.getName());
        User user  = userRepository.findByEmail(authentication.getName());
        log.info("user : {}", user);
        if(user != null) {
            if(bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
                List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
                for(UserRole userRole : user.getUserRoles()) {
                    grantedAuthorityList.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
                }
                return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorityList);
            } else {
                throw new BadCredentialsException("패스워드 정보가 다릅니다.");
            }
        } else {
            throw new BadCredentialsException("이메일 정보가 다릅니다.");
        }
    }
}

package com.study.user.service;

import com.study.user.dto.UserDto;
import com.study.user.model.User;
import com.study.user.repository.UserRepository;
import com.study.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        UserDto userDto = new UserDto();
        userDto.setUserId(email);
        userDto.setPassword(user.getPassword());
        userDto.setAccountNonExpired(false);
        userDto.setAccountNonLocked(false);
        userDto.setEnabled(false);
        userDto.setCredentialsNonExpired(false);
        userDto.setAuthorities(this.getAuthorities(email));

        return userDto;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String email) {
        //userRoleRepository.findBy(email);
//        List<Authority> authList = authoritiesRepository.findByUsername(username);
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Authority authority : authList) {
//            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
//        }
//        return authorities;

        return null;

    }
}

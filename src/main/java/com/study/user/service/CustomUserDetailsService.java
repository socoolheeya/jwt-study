package com.study.user.service;

import com.study.user.dto.UserDto;
import com.study.user.model.User;
import com.study.user.repository.RoleRepository;
import com.study.user.repository.UserRepository;
import com.study.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private List<String> roles = new ArrayList<>();


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
        userDto.setAuthorities(this.getAuthorities(this.roles));

        return userDto;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return authorities;
    }
}

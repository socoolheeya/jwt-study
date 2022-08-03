package com.study.user;

import com.study.user.model.Role;
import com.study.user.model.User;
import com.study.user.model.UserRole;
import com.study.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 사용자_등록_테스트() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("1234");

        List<UserRole> userRoles = new ArrayList<>();
        UserRole userRole = new UserRole();

        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        userRole.setRole(role);


        User user = User.builder()
                .name("test")
                .password(password)
                .email("lwh0102@gmail.com")
                .userRoles(userRoles)
                .build();

        userRepository.save(user);
    }

}

package com.study.main.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    String userId;
    String loginAccount;
    String password;
}

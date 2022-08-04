package com.study.user.model.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUser {

    private Long id;
    private String email;
    private String name;
    private String password;
}

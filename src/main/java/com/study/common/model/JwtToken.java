package com.study.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class JwtToken {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(columnDefinition = "text comment '엑세스 키'")
    private String accessTokenKey;

    @Column(columnDefinition = "text comment '리프레시 키'")
    private String refreshTokenKey;
}

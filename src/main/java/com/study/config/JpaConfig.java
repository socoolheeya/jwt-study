package com.study.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {
    private final EntityManager entityManager;
    @Bean
    public JpaRepositoryFactory jpaRepositoryFactory() {
        return new JpaRepositoryFactory(entityManager);
    }
}

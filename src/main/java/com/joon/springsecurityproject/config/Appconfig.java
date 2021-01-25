package com.joon.springsecurityproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Appconfig {
    @Bean
    public PasswordEncoder passwordEncoder(){ //{noop}로 만들어줌
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

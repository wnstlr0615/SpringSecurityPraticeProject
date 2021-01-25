package com.joon.springsecurityproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()  //접근 설정
                .mvcMatchers("/", "/info").permitAll() //모두 허용
                .mvcMatchers("/admin").hasRole("ADMIN") //자격 요건
                .anyRequest().authenticated(); //로그인 사용자는 가능
        http.formLogin();
        http.httpBasic();
        ;
    }
}

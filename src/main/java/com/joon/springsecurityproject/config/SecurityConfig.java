package com.joon.springsecurityproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 사용자 추가 기능 메소드
        auth.inMemoryAuthentication()
                .withUser("joon").password("{noop}123").roles("USER")// {noop} Spring 5부터 사용가능 한 기본 비밀번호 인코더(암호화 x)
                .and()
                .withUser("admin").password("{noop}!@#").roles("ADMIN");//암호화 해서 전송

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()  //접근 설정
                .mvcMatchers("/", "/info").permitAll() //모두 허용
                .mvcMatchers("/admin").hasRole("ADMIN") //자격 요건
                .anyRequest().authenticated(); //로그인 사용자는 가능
        http.formLogin();
        http.httpBasic();
    }

}

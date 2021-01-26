package com.joon.springsecurityproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 사용자 추가 기능 메소드
        auth.inMemoryAuthentication()
                .withUser("joon").password("{noop}123").roles("USER")// {noop} Spring 5부터 사용가능 한 기본 비밀번호 인코더(암호화 x)
                .and()
                .withUser("admin").password("{noop}!@#").roles("ADMIN");//암호화 해서 전송

    }*/

   /* public AccessDecisionManager accessDecisionManager(){  // 권한 세부 설정
        RoleHierarchyImpl roleHierarchy=new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN>ROLE_USER");
        DefaultWebSecurityExpressionHandler handler=new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        WebExpressionVoter webExpressionVoter=new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(handler);
        List<AccessDecisionVoter<? extends Object>> voters= Arrays.asList(webExpressionVoter);
        return new AffirmativeBased(voters);
    }*/
   public SecurityExpressionHandler expressionHandler(){ // // 권한 세부 설정
       RoleHierarchyImpl roleHierarchy=new RoleHierarchyImpl();
       roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
       DefaultWebSecurityExpressionHandler handler=new DefaultWebSecurityExpressionHandler();
       handler.setRoleHierarchy(roleHierarchy);
        return handler;
   }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()  //접근 설정
                .mvcMatchers("/", "/info", "/account/**").permitAll() //모두 허용
                .mvcMatchers("/admin").hasRole("ADMIN") //자격 요건
                .mvcMatchers("/user").hasRole("USER") //자격 요건
                .anyRequest().authenticated()//로그인 사용자는 가능
               // .accessDecisionManager(accessDecisionManager())
                .expressionHandler(expressionHandler())
        ;
        http.formLogin();
        http.httpBasic();
    }

}

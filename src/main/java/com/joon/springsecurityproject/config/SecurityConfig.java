package com.joon.springsecurityproject.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public SecurityExpressionHandler expressionHandler(){ // // 권한 세부 설정
       RoleHierarchyImpl roleHierarchy=new RoleHierarchyImpl();
       roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
       DefaultWebSecurityExpressionHandler handler=new DefaultWebSecurityExpressionHandler();
       handler.setRoleHierarchy(roleHierarchy);
        return handler;
   }
    @Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().mvcMatchers("/favicon.ico");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());// favicon.ico 등 리소스 허용
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()  //접근 설정
                .mvcMatchers("/", "/info", "/account/**", "/signup").permitAll() //모두 허용
                .mvcMatchers("/admin").hasRole("ADMIN") //자격 요건
                .mvcMatchers("/user").hasRole("USER") //자격 요건
                //.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() 결과는 같지만 요청시간 이 오래 걸림
                .anyRequest().authenticated()//로그인 사용자는 가능
                .expressionHandler(expressionHandler())
            // .accessDecisionManager(accessDecisionManager())
        ;
        http.formLogin()
            .loginPage("/login")
            .permitAll()
        ;
       // SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);//ThreadLocal 하위 범위까지 공유하도록 설정
    }




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

}

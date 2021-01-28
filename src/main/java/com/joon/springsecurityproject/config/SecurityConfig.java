package com.joon.springsecurityproject.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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


        http.exceptionHandling() //ExceptionTranslationFilter 커스터 마이징
        //        .accessDeniedPage("/access-denied")  //페이지 전환
                    .accessDeniedHandler(new AccessDeniedHandler() { // 좀더 세부 적으러 기록 및 전화
                        @Override
                        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                            UserDetails principal= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                            String name=principal.getUsername();
                            System.out.println(name+" is denied to access "+ request.getRequestURI());
                            response.sendRedirect("/access-denied");
                        }
                    })
        ;


      /*  세션 커스터 마이징
       http.sessionManagement()
                .sessionFixation()
                    .changeSessionId()
                        .invalidSessionUrl("login") //세션 변경시 보내질 url
                    .maximumSessions(1) // 한 계졍으로 접근 가능한 개수
                        .maxSessionsPreventsLogin(true) //true 시 추가 로그인 허용 x
                        .expiredUrl("/")  //외부에서 로그인 시도시 기존 앱에서 보내질 url
                .and()
                    .sessionCreationPolicy()//세션 생선 전략
                    ;
                    */


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

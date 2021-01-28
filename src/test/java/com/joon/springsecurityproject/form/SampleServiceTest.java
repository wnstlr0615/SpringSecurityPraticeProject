package com.joon.springsecurityproject.form;

import com.joon.springsecurityproject.account.Account;
import com.joon.springsecurityproject.account.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTest {
    @Autowired
    SampleService sampleService;
    @Autowired
    AccountService accountService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    //@WithMockUser로 대체 가능
    public void dashboard_user(){
        Account account=Account.builder()
                        .username("joon")
                        .password("1234")
                        .role("USER")
                        .build();
        accountService.createUser(account);
        UserDetails userDetails = accountService.loadUserByUsername(account.getUsername());
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userDetails,"1234");//credentials는 패스워드
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        sampleService.dashboard();
    }
    @Test
    //@WithMockUser로 대체 가능
    public void dashboard_admin(){
        Account account=Account.builder()
                .username("joon1")
                .password("1234")
                .role("ADMIN")
                .build();
        accountService.createUser(account);
        UserDetails userDetails = accountService.loadUserByUsername(account.getUsername());
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userDetails,"1234");//credentials는 패스워드
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        sampleService.dashboard();
    }
}
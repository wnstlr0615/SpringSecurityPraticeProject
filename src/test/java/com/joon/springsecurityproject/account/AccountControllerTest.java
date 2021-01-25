package com.joon.springsecurityproject.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    AccountService accountService;
    @Test
    @WithAnonymousUser
    public void inedx_anonymous() throws Exception {
        mvc.perform(get("/"))//User가 로그인했다고 가정
                    .andDo(print())
                    .andExpect(status().isOk());
    }
    @Test
    @WithUser
    public void inedx_user() throws Exception {
        mvc.perform(get("/"))//User가 로그인했다고 가정
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void admin_user() throws Exception {
        mvc.perform(get("/admin").with(user("joon").roles("USER")))//User가 로그인했다고 가정
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(username = "joon",roles = {"ADMIN"})
    public void admin_admin() throws Exception {
        mvc.perform(get("/admin"))//User가 로그인했다고 가정
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void login_success()throws Exception{
        String username = "joon";
        String password = "123";
        Account account=createUser(username, password);
        mvc.perform(formLogin().user(account.getUsername()).password(password))
                .andExpect(authenticated());//로그인 성공 여부
    }
    @Test
    @Transactional
    public void login_fail()throws Exception{
        String username = "joon";
        String password = "123";
        Account account=createUser(username, password);
        mvc.perform(formLogin().user(account.getUsername()).password("12345"))
                .andExpect(unauthenticated());//로그인 성공 여부
    }

    private Account createUser(String username, String password) {
        Account account=Account.builder()
                        .username(username)
                        .password(password)
                        .role("USER")
                        .build();
        accountService.createUser(account);
        return account;
    }
}
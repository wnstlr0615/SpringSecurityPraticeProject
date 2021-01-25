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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    MockMvc mvc;

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
}
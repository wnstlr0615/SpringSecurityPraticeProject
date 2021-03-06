package com.joon.springsecurityproject.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignUpControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    public void signUpForm() throws Exception {
        mvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("_csrf")))
        ;
    }

    @Test
    public void processSignUp() throws Exception {
        mvc.perform(post("/signup")
                .param("username", "joon")
                .param("password", "123")
                .with(csrf()) //csrf 토큰을 넣는 방법
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection());//리다이렉션 확인
    }
}
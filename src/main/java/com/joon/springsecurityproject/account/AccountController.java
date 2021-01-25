package com.joon.springsecurityproject.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;
    @GetMapping("/account/{role}/{username}/{password}")// 임의로 만든 간단한 아이디 생성 API
    public Account createAccount(@ModelAttribute Account account){
        return accountService.createUser(account);
    }
}

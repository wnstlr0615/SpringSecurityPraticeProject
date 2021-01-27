package com.joon.springsecurityproject.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    @Autowired
    AccountService accountService;
    @GetMapping
    public String signupFomr(Model model){
        model.addAttribute("account", new Account());
        return "signup";
    }
    @PostMapping
    public String processSignUp(@ModelAttribute Account account){
        account.setRole("USER");
        accountService.createUser(account);
        return "redirect:/";
    }
}

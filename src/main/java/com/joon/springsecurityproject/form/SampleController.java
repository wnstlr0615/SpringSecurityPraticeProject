package com.joon.springsecurityproject.form;

import com.joon.springsecurityproject.account.Account;
import com.joon.springsecurityproject.account.UserAccount;
import com.joon.springsecurityproject.book.BookRepository;
import com.joon.springsecurityproject.common.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SampleController {

    @Autowired
    SampleService sampleService;
    @Autowired
    BookRepository bookRepository;

    @GetMapping("/")
  //  public String index(Model model, Principal principal){ 변경 전
  //   public String index(Model model, @AuthenticationPrincipal UserAccount userAccount){
    public String index(Model model,@CurrentUser Account userAccount){

            if(userAccount==null){
            model.addAttribute("message", "Hello Spring Security");
        }else{
            model.addAttribute("message", "Hello "+userAccount.getUsername());
        }
        return "index";
    }
    @GetMapping("/info")
    public String info(Model model){
        model.addAttribute("message", "Info");
        return "info";
    }
    @GetMapping("/admin")
    public String admin(Model model, Principal principal){
        model.addAttribute("message", "Hello "+ principal.getName());
        return "admin";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("message", "Hello"+principal.getName());
        sampleService.dashboard();
        return "dashboard";
    }
    @GetMapping("/user")
    public String user(Model model, Principal principal){
        model.addAttribute("message", "Hello user"+principal.getName());
        model.addAttribute("books", bookRepository.findcurrentUserBook());
        return "user";
    }

}

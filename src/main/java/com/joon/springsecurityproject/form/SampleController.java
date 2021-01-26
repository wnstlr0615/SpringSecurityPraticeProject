package com.joon.springsecurityproject.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SampleController {

    @Autowired
    SampleService sampleService;
    @GetMapping("/")
    public String index(Model model, Principal principal){
        if(principal==null){
            model.addAttribute("message", "Hello Spring Security");
        }else{
            model.addAttribute("message", "Hello "+principal.getName());
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
        sampleService.dashboard();
        return "user";
    }

}

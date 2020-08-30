package com.app.blog.controller;

import com.app.blog.model.User;
import com.app.blog.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String viewRegisterPage(Model model){
        User user = new User();
        model.addAttribute("users", user);
        return "register";
    }
    @GetMapping("/signIn")
    public String viewUserSignIn(Model model){
        User user = new User();
        model.addAttribute("users", user);
        return "sign_in";
    }

    @GetMapping ("/registerUser")
    public String registerUser(@ModelAttribute("users") User user){
        user.setRole("AUTHOR");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDetailsServiceImpl.saveUserDetails(user);
        return "sign_in";
    }
}

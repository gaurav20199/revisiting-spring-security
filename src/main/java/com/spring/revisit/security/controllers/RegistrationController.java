package com.spring.revisit.security.controllers;

import com.spring.revisit.security.dto.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RegistrationController {

    //private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final UserDetailsManager userDetailsManager;

    private final PasswordEncoder passwordEncoder;

    RegistrationController(UserDetailsManager userDetailsManager,PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration-form";
    }

    @PostMapping("/register")
    public String submitRegistration(@ModelAttribute User user, Model model) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("FREE_TRIAL"));
        String password = passwordEncoder.encode(user.getPassword());
        org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(user.getUserName(), password,authorities);
        userDetailsManager.createUser(securityUser);
        model.addAttribute("message", "Registration successful!");
        return "registration-success";
    }
}

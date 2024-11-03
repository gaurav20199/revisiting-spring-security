package com.spring.revisit.security.controllers;

import com.spring.revisit.security.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration-form";
    }

    @PostMapping("/register")
    public String submitRegistration(@ModelAttribute User user, Model model) {
        model.addAttribute("message", "Registration successful!");
        return "registration-success";
    }
}

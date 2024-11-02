package com.spring.revisit.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePageController {

    @GetMapping("/home")
    public String showHomePageContent() {
        return "Welcome to the Home page";
    }

    @GetMapping("/")
    public String showAuthenticationSuccessMsg() {
        return "Authentication Successful!!";
    }
}

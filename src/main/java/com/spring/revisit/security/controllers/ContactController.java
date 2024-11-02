package com.spring.revisit.security.step1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @GetMapping("/about")
    public String showContentForContactPage() {
        return "Welcome to the Contact Us Page!!";
    }
}

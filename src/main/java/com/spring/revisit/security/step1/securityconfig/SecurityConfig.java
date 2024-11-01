package com.spring.revisit.security.step1.securityconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

}

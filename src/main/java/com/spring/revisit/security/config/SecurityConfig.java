package com.spring.revisit.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Autowired
    HttpSecurity httpSecurity;

/*    @Bean
    InMemoryUserDetailsManager setUpUser() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("admin"));
        UserDetails user = new User("gaurav","gaurav",authorities);
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(user);
        return inMemoryUserDetailsManager;
    }*/

    @Bean
    InMemoryUserDetailsManager setUpUser() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("admin"));
        UserDetails user = User.withUsername("gaurav").password("{noop}gaurav").authorities(authorities).build();
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(user);
        return inMemoryUserDetailsManager;
    }

    @Bean
    SecurityFilterChain customSecurityFilterChain() throws Exception {
        return httpSecurity.authorizeHttpRequests().anyRequest().authenticated()
                .and().formLogin().and().httpBasic().and().build();
    }

/*    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }*/

}

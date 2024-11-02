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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

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
//        use this when you want to authenticate every request
//        return httpSecurity.authorizeHttpRequests().anyRequest().authenticated()
//                .and().formLogin().and().httpBasic().and().build();

        // For about we don't require any authentication
        httpSecurity.authorizeHttpRequests().requestMatchers("/about").denyAll();
        httpSecurity.authorizeHttpRequests().requestMatchers("/home").permitAll();
        httpSecurity.authorizeHttpRequests().requestMatchers("/").authenticated();

          // If we are configuring using AntPathRequestMatcher then we don't require mvcHandlerMappingIntrospector bean
//        httpSecurity.authorizeHttpRequests().requestMatchers(AntPathRequestMatcher.antMatcher("/about")).denyAll();
//        httpSecurity.authorizeHttpRequests().requestMatchers(AntPathRequestMatcher.antMatcher("/home")).permitAll();
//        httpSecurity.authorizeHttpRequests().requestMatchers(AntPathRequestMatcher.antMatcher("/")).authenticated();


        httpSecurity.formLogin();
        httpSecurity.httpBasic();
        return httpSecurity.build();
    }

    @Bean("mvcHandlerMappingIntrospector")
    HandlerMappingIntrospector handlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

/*    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }*/

}

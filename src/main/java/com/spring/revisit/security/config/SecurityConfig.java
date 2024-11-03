package com.spring.revisit.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
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
/*
    Above Spring Security version 6.0.0 authorizeHttpRequest(), formLogin() and HttpBasic()
    has been deprecated.
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

 */

    @Bean
    SecurityFilterChain customSecurityFilterChainLatest(HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
//        httpSecurity.authorizeHttpRequests(new Customizer<AuthorizeHttpRequestsConfigurer<org.springframework.security.config.annotation.web.builders.HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>() {
//            @Override
//            public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
//                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/about")).denyAll();
//                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/home")).permitAll();
//                authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
//            }
//        }).formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());

        // since customizer is a functional interface we can use lambda directly
        httpSecurity.authorizeHttpRequests(customizer -> {
                    customizer.requestMatchers(mvcMatcherBuilder.pattern("/about")).denyAll();
                    customizer.requestMatchers(mvcMatcherBuilder.pattern("/home")).permitAll();
                    customizer.anyRequest().authenticated();
        }).formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
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

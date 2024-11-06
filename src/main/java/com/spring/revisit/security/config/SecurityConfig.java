package com.spring.revisit.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import javax.sql.DataSource;

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
/*
    @Bean
    InMemoryUserDetailsManager setUpUser() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("admin"));
        UserDetails user = User.withUsername("Admin").password("{noop}Admin").authorities(authorities).build();
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(user);
        return inMemoryUserDetailsManager;
    }
 */

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("Iltwat");
        dataSource.setUrl("jdbc:mysql://localhost:3306/securitydemo");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }

    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
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
                    customizer.requestMatchers(mvcMatcherBuilder.pattern("/register")).permitAll();
                    customizer.anyRequest().authenticated();
        }).formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
                //.csrf(customizer -> customizer.disable());
        // disabling csrf for post mappings as it is enabled by default and post requests are secured by default
        return httpSecurity.build();
    }

    @Bean("mvcHandlerMappingIntrospector")
    HandlerMappingIntrospector handlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

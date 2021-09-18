package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.demo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    // Setting up the local variable in the constructor
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home") .permitAll()
                .antMatchers("/students/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    // Setting up the InMemory user details
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        String encodedPass=passwordEncoder.encode("password");
        UserDetails markHunt = User.builder()
                .username("markHunt")
                .password(encodedPass)
                .roles(STUDENT.name()) // ROLE_STUDENT(Internal name - Given by Spring)
                .build();
        encodedPass=passwordEncoder.encode("password123");
        UserDetails stevenStreak = User.builder()
                .username("stevenStreak")
                .password(encodedPass)
                .roles(ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(markHunt, stevenStreak);
    }
}

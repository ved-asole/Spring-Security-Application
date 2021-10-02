package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.demo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    // Setting up the local variable in the constructor
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Setting up the Spring Security Configuration
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home") .permitAll()
                .antMatchers("/students/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                    // For form based login
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/courses", true)
                    .failureForwardUrl("/")
                .and()
                    // For RememberMe Functionality
                    .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("verySecureKey")
                .and()
                    // For logout feature
                    .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID","remember-me")
                    .logoutSuccessUrl("/login");
    }

    // Setting up the InMemory user details
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        String encodedPass=passwordEncoder.encode("password");
        UserDetails markHunt = User.builder()
                .username("markHunt")
                .password(encodedPass)
                //.roles(STUDENT.name()) // ROLE_STUDENT(Internal name - Given by Spring)
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        encodedPass=passwordEncoder.encode("password123");
        UserDetails stevenStreak = User.builder()
                .username("stevenStreak")
                .password(encodedPass)
                //.roles(ADMIN.name()) // ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        encodedPass=passwordEncoder.encode("mypassword");
        UserDetails erakesh = User.builder()
                .username("erakesh")
                .password(encodedPass)
                //.roles(TRAINEE.name()) // ROLE_TRAINEE
                .authorities(TRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(markHunt, stevenStreak, erakesh);
    }

}

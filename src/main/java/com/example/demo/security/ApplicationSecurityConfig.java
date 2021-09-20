package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home") .permitAll()
                .antMatchers("/students/**").hasRole(STUDENT.name())
                .antMatchers("/management/**").hasAnyRole(STUDENT.name(), TRAINEE.name())
                .antMatchers(HttpMethod.GET,"/management/**").hasAnyAuthority(ADMIN.name(), TRAINEE.name())
                .antMatchers(HttpMethod.POST,"/management/**").hasAuthority(ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/management/**").hasAuthority(ADMIN.name())
                .antMatchers(HttpMethod.DELETE,"/management/**").hasAuthority(ADMIN.name())
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

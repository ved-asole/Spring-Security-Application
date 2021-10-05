package com.example.demo.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import static com.example.demo.security.ApplicationUserRole.ADMIN;
import static com.example.demo.security.ApplicationUserRole.STUDENT;

@Repository("userRepo")
public class ApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Getting the user with the required username
    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    // Setting up all the users for authentication
    private List<ApplicationUser> getApplicationUsers() {

        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "markHunt",
                        passwordEncoder.encode("mark@123"),
                        STUDENT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                   ),
                new ApplicationUser(
                        "jonasBlue",
                        passwordEncoder.encode("jonus@123"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "stanLee",
                        passwordEncoder.encode("stanlee@123"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )

        );
        return applicationUsers;
    }

}

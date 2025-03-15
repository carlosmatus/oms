package com.demo.oms.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    public static final String USER_NOT_FOUND_WITH_USERNAME = "User not found with username: ";
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if ("user".equals(username)) {
            return User.builder()
                    .username("user")
                    .password(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("password"))
                    .roles(USER)
                    .build();
        }

        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("password"))
                    .roles(ADMIN)
                    .build();
        }

        throw new UsernameNotFoundException(USER_NOT_FOUND_WITH_USERNAME + username);
    }
}


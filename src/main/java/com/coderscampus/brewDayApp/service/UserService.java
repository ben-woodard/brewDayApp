package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    List<User> findAll();
}
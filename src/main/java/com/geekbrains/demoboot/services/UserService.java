package com.geekbrains.demoboot.services;

import com.geekbrains.demoboot.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);
}

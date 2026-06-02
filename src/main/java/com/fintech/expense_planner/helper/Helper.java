package com.fintech.expense_planner.helper;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fintech.expense_planner.model.User;
import com.fintech.expense_planner.repository.UserRepository;


import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Helper {
    private final UserRepository userRepository;

    public User getLoggedInUser(){
        String username = SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getName();
        
        User user = userRepository.findByUsername(username)
                            .orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

}

package com.fintech.expense_planner.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fintech.expense_planner.dto.NotificationDto;
import com.fintech.expense_planner.helper.Helper;
import com.fintech.expense_planner.model.User;
import com.fintech.expense_planner.service.NotificationService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {   
    private final NotificationService service;
    private final Helper helper;

    @GetMapping
    public ResponseEntity<List<NotificationDto.Response>> getAll(){
        User user = helper.getLoggedInUser();
        return ResponseEntity.ok(service.getAll(user)
                        .stream()
                        .map(NotificationDto::from)
                        .collect(Collectors.toList())
        );
    }
    
    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDto.Response>> getUnread(){
        User user = helper.getLoggedInUser();
        return ResponseEntity.ok(service.getUnread(user)
                        .stream()
                        .map(NotificationDto::from)
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("/mark-read")
    public void markAsRead(){
        User user = helper.getLoggedInUser();
        service.markAllRead(user);
    }
}

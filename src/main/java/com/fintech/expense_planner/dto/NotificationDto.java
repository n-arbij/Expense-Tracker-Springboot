package com.fintech.expense_planner.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fintech.expense_planner.model.Notification;
import com.fintech.expense_planner.model.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
public class NotificationDto {
    @Data
    @AllArgsConstructor
    public static class Response {
        private UUID id;
        private String message;
        private NotificationType type;
        private boolean read;
        private LocalDateTime createdAt;
    }

    public static Response from(Notification notification){
        return new Response(
            notification.getId(),
            notification.getMessage(),
            notification.getType(),
            notification.isRead(),
            notification.getCreatedAt()
        );
    }
}

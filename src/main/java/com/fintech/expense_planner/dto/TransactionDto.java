package com.fintech.expense_planner.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fintech.expense_planner.model.TransactionType;
import com.fintech.expense_planner.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
public class TransactionDto {
    @Data
    public static class Create{
        private User user;
        private BigDecimal amount;
        private String description;
        private TransactionType type;
        private UUID categoryId;
        private UUID goalId;
    }

    @Data
    public static class Update{
        private BigDecimal amount;
        private String description;
        private TransactionType type;
        private UUID categoryId;
        private UUID goalId;
    }

    @Data
    @AllArgsConstructor
    public static class Response{
        private UUID id;
        private BigDecimal amount;
        private String description;
        private TransactionType type;
        private UUID categoryId;
        private String categoryName;
        private UUID goalId;
        private String goalName;
        private LocalDate date;
    }
}

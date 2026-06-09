package com.fintech.expense_planner.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.fintech.expense_planner.model.GoalStatus;
import com.fintech.expense_planner.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

public class GoalDto {
    @Data
    public static class Create{
        private User user;
        private String name;
        private BigDecimal targetAmount;
    }

    @Data
    public static class Update{
        private String name;
        private BigDecimal targetAmount;
    }

    @Data
    @AllArgsConstructor
    public static class Response{
        private UUID id;
        private String name;
        private GoalStatus status;
        private BigDecimal targetAmount;
        private BigDecimal savedAmount;
    }

    @Data
    @AllArgsConstructor
    public static class Summary{
        private UUID id;
        private String name;
        private GoalStatus status;
        private BigDecimal targetAmount;
        private BigDecimal savedAmount;
        private double percentageComplete;
        private boolean isComplete;
    }
}

package com.fintech.expense_planner.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

public class BudgetDto {
    @Data
    public static class Create{
        private BigDecimal amount;
        private YearMonth month;
        private UUID categoryId;
    }

    @Data
    public static class Update{
        private BigDecimal amount;
        private YearMonth month;
        private UUID categoryId;
    }

    @Data
    @AllArgsConstructor
    public static class Response{
        private UUID id;
        private BigDecimal amount;
        private YearMonth month;
        private UUID categoryId;
        private String categoryName;
    }

    @Data
    @AllArgsConstructor
    public static class Summary{
        private UUID id;
        private String categoryName;
        private YearMonth month;
        private BigDecimal budgetAmount;
        private BigDecimal spent;
        private BigDecimal remaining;
        private boolean isExceeded;
        private double percentageUsed;
    }
}

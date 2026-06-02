package com.fintech.expense_planner.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

public class CategoryDto {
    @Data
    public static class Create{
        private UUID userId;
        private String name;
        private String color;
    }

    @Data
    public static class Update{
        private String name;
        private String color;
    }

    @Data
    @AllArgsConstructor
    public static class Response{
        private UUID id;
        private String name;
        private String color;
    }
}

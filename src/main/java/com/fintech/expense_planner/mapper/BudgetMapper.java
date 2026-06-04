package com.fintech.expense_planner.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fintech.expense_planner.dto.BudgetDto;
import com.fintech.expense_planner.model.Budget;

@Component
public class BudgetMapper {
    public BudgetDto.Response toResponse(Budget budget){
        return new BudgetDto.Response(
            budget.getId(),
            budget.getAmount(),
            budget.getMonth(),
            budget.getCategory().getId(),
            budget.getCategory().getName()
        );
    }

    public List<BudgetDto.Response> toResponseList(List<Budget> budgets){
        return  budgets.stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
    }
}

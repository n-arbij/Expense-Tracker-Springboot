package com.fintech.expense_planner.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import com.fintech.expense_planner.dto.GoalDto;
import com.fintech.expense_planner.model.Goal;

@Component
public class GoalMapper {

    public GoalDto.Response toResponse(Goal goal) {
        return new GoalDto.Response(
            goal.getId(),
            goal.getName(),
            goal.getStatus(),
            goal.getTargetAmount(),
            goal.getSavedAmount()
        );
    }

    public List<GoalDto.Response> toResponseList(List<Goal> goals) {
        return goals.stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
    }
}

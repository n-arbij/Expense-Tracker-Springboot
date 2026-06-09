package com.fintech.expense_planner.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fintech.expense_planner.dto.GoalDto;
import com.fintech.expense_planner.helper.Helper;
import com.fintech.expense_planner.model.Goal;
import com.fintech.expense_planner.model.GoalStatus;
import com.fintech.expense_planner.model.User;
import com.fintech.expense_planner.repository.GoalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalRepository repository;
    private final Helper helper;

    public Goal getById(UUID id) {
        return repository.findById(id).orElseThrow(
            () -> new RuntimeException("Goal not found")
        );
    }

    public GoalDto.Summary getSummary(UUID id){
        Goal goal = getById(id);

        double pct = goal.getSavedAmount()
                        .divide(goal.getTargetAmount())
                        .multiply(BigDecimal.valueOf(100))
                        .doubleValue();
        
        boolean isComplete = goal.getSavedAmount().compareTo(goal.getTargetAmount()) >= 0;

        return new GoalDto.Summary(
            id,
            goal.getName(),
            goal.getStatus(),
            goal.getTargetAmount(),
            goal.getSavedAmount(),
            pct,
            isComplete
        );
    }

    public List<Goal> getAll() {
        User user = helper.getLoggedInUser();
        return repository.findAllByUserAndIsDeletedFalse(user);
    }

    public void createGoal(GoalDto.Create goalDto){
        User user = helper.getLoggedInUser();

        Goal goal = new Goal();
        goal.setName(goalDto.getName());
        goal.setStatus(GoalStatus.IN_PROGRESS);
        goal.setTargetAmount(goalDto.getTargetAmount());
        goal.setSavedAmount(BigDecimal.ZERO);
        goal.setUser(user);
        repository.save(goal);
    }

    public void updateGoal(UUID id, GoalDto.Update goalDto){
        Goal goal = getById(id);
        goal.setName(goalDto.getName());
        goal.setTargetAmount(goalDto.getTargetAmount());
        repository.save(goal);
    }

    public void deleteGoal(UUID id){
        Goal goal = getById(id);
        goal.setDeleted(true);
        goal.setStatus(GoalStatus.CANCELLED);
        repository.save(goal);
    }
}

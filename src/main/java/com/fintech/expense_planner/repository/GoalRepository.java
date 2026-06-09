package com.fintech.expense_planner.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fintech.expense_planner.model.Goal;
import com.fintech.expense_planner.model.User;

@Repository
public interface GoalRepository extends JpaRepository<Goal, UUID>{
    List<Goal> findAllByUserAndIsDeletedFalse(User user);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.goal = :goal AND t.type = 'SAVING'")
    BigDecimal sumSavedByGoal(@Param("goal") Goal goal);
}

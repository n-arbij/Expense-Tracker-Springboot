package com.fintech.expense_planner.repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fintech.expense_planner.model.Budget;
import com.fintech.expense_planner.model.User;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID>{
    List<Budget> findAllByUserAndIsDeletedFalse(User user);
    Optional<Budget> findByUserAndCategoryIdAndMonth(User user, UUID categoryId, YearMonth month);
}

package com.fintech.expense_planner.repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fintech.expense_planner.model.Budget;
import com.fintech.expense_planner.model.User;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, UUID>{
    List<Budget> findAllByUserAndIsDeletedFalse(User user);

    @Query("SELECT b FROM Budget b WHERE b.user = :user AND b.category.id = :categoryId AND b.month = :month AND b.isDeleted = false")
    Optional<Budget> findByUserAndCategoryIdAndMonth(
        @Param("user") User user,
        @Param("categoryId") UUID categoryId,
        @Param("month") YearMonth month);
}

package com.fintech.expense_planner.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fintech.expense_planner.model.Category;
import com.fintech.expense_planner.model.Transaction;
import com.fintech.expense_planner.model.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID>, 
    JpaSpecificationExecutor<Transaction> {

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
       "WHERE t.user = :user " +
       "AND t.category = :category " +
       "AND t.type = 'EXPENSE' " +
       "AND t.isDeleted = false " +
       "AND FUNCTION('DATE_FORMAT', t.date, '%Y-%m') = :month")
    BigDecimal sumSpentByUserAndCategoryAndMonth(
        @Param("user") User user,
        @Param("category") Category category,
        @Param("month") String month
    );
}

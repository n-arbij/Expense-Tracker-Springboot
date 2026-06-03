package com.fintech.expense_planner.specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.fintech.expense_planner.model.Category;
import com.fintech.expense_planner.model.Transaction;
import com.fintech.expense_planner.model.TransactionType;
import com.fintech.expense_planner.model.User;

public class TransactionSpecification {
    public static Specification<Transaction> belongsToUser(User user){
        return (root, query, cb) -> cb.equal(root.get("user"), user);
    }
    
    public static Specification<Transaction> hasType(TransactionType transactionType){
        return (root, query, cb) -> cb.equal(root.get("type"), transactionType);
    }
    
    public static Specification<Transaction> hasCategory(Category category){
        return (root, query, cb) -> cb.equal(root.get("category"), category);
    }
    
    public static Specification<Transaction> amountGreaterThanOrEqual(BigDecimal min){
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("amount"), min);
    }
    
    public static Specification<Transaction> amountLessThanOrEqual(BigDecimal max){
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("amount"), max);
    }
    public static Specification<Transaction> inMonth(String yearMonth){
        return (root, query, cb) -> {
            LocalDate start = LocalDate.parse(yearMonth + "-01");
            LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
            return cb.between(root.get("date"), start, end);
        };
    }

    public static Specification<Transaction> isNotDeleted(){
        return (root, query, cb) -> cb.equal(root.get("isDeleted"), false);
    }

}

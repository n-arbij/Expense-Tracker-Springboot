package com.fintech.expense_planner.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fintech.expense_planner.dto.BudgetDto;
import com.fintech.expense_planner.helper.Helper;
import com.fintech.expense_planner.model.Budget;
import com.fintech.expense_planner.model.Category;
import com.fintech.expense_planner.model.User;
import com.fintech.expense_planner.repository.BudgetRepository;
import com.fintech.expense_planner.repository.CategoryRepository;
import com.fintech.expense_planner.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final Helper helper;

    public Budget getBudgetById(UUID id){
        System.out.println("Budget ID: " + id);
        return budgetRepository.findById(id).orElseThrow(() -> new RuntimeException("Budget not found"));
    }

    public List<Budget> getAllBudgets(){
        User user = helper.getLoggedInUser();
        return budgetRepository.findAllByUserAndIsDeletedFalse(user);
    }

    public void createBudget(BudgetDto.Create budgetDto){
        User user = helper.getLoggedInUser();

        Category category = categoryRepository.findById(budgetDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

        budgetRepository.findByUserAndCategoryIdAndMonth(user, budgetDto.getCategoryId(), budgetDto.getMonth())
                    .ifPresent(b -> {
                        throw new RuntimeException("Budget already exists for this category and month");
                    });


        Budget budget = new Budget();
        budget.setAmount(budgetDto.getAmount());
        budget.setMonth(budgetDto.getMonth());
        budget.setCategory(category);
        budget.setUser(user);
        budgetRepository.save(budget);
    }

    public void updateBudget(UUID id, BudgetDto.Update budgetDto){
        User user = helper.getLoggedInUser();
        
        Budget budget = budgetRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Budget not found"));
        
        if(budget.isDeleted()){
            throw new RuntimeException("Budget not found");
        }
        if(!budget.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Access denied");
        }
        
        Category category = categoryRepository.findById(budgetDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

        budget.setAmount(budgetDto.getAmount());
        budget.setMonth(budgetDto.getMonth());
        budget.setCategory(category);
        budgetRepository.save(budget);
    }

    public void deleteBudget(UUID id){
        User user = helper.getLoggedInUser();
        Budget budget = budgetRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Budget not found"));

        if (budget.isDeleted()){
            throw new RuntimeException("Budget not found");
        }

        if(!budget.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Access denied");
        }

        budget.setDeleted(true);
        budgetRepository.save(budget);
    }

    public BudgetDto.Summary getSummary(UUID id){
        User user = helper.getLoggedInUser();
        Budget budget = budgetRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Budget not found"));

        if(budget.isDeleted()){
            throw new RuntimeException("Budget not found");
        }
        if(!budget.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Access denied");
        }

        BigDecimal spent = transactionRepository.sumSpentByUserAndCategoryAndMonth(
                user,
                budget.getCategory(),
                budget.getMonth().toString()
        );

        BigDecimal remaining = budget.getAmount().subtract(spent);
        boolean isExceeded = spent.compareTo(budget.getAmount()) > 0;
        double percentageUsed = spent
                            .divide(budget.getAmount(), 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
                            .doubleValue();

        return new BudgetDto.Summary(
            id,
            budget.getCategory().getName(),
            budget.getMonth(),
            budget.getAmount(),
            spent,
            remaining,
            isExceeded,
            percentageUsed
        );
    }
}

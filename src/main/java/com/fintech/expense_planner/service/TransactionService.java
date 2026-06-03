package com.fintech.expense_planner.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fintech.expense_planner.dto.TransactionDto;
import com.fintech.expense_planner.helper.Helper;
import com.fintech.expense_planner.model.Transaction;
import com.fintech.expense_planner.model.Category;
import com.fintech.expense_planner.model.TransactionType;
import com.fintech.expense_planner.model.User;
import com.fintech.expense_planner.repository.CategoryRepository;
import com.fintech.expense_planner.repository.TransactionRepository;
import com.fintech.expense_planner.specification.TransactionSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final Helper helper;

    public Transaction getTransactionById(UUID id){
        User user = helper.getLoggedInUser();
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));

        if(transaction.isDeleted()){
            throw new RuntimeException("Transaction not found");
        }

        if (!transaction.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Access denied");
        }
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found!"));
    }

    public List<Transaction> getAllTransactions(
        TransactionType type,
        Category category,
        BigDecimal minAmount,
        BigDecimal maxAmount,
        String date
    ) {
        Specification<Transaction> spec = TransactionSpecification.belongsToUser(helper.getLoggedInUser())
            .and(TransactionSpecification.isNotDeleted());
            
        if(type != null){
            spec = spec.and(TransactionSpecification.hasType(type));
        }
        if(category != null){
            spec = spec.and(TransactionSpecification.hasCategory(category));
        }
        if(minAmount != null){
            spec = spec.and(TransactionSpecification.amountGreaterThanOrEqual(minAmount));
        }
        if(maxAmount != null){
            spec = spec.and(TransactionSpecification.amountLessThanOrEqual(maxAmount));
        }
        if(date != null){
            spec = spec.and(TransactionSpecification.inMonth(date));
        }

        return transactionRepository.findAll(spec);
    }

    public void createTransaction(TransactionDto.Create transcationDto){
        Category category = categoryRepository.findById(transcationDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Transaction transaction = new Transaction();
        transaction.setAmount(transcationDto.getAmount());
        transaction.setDescription(transcationDto.getDescription());
        transaction.setType(transcationDto.getType());
        transaction.setCategory(category);
        transaction.setDate(LocalDate.now());
        transaction.setUser(helper.getLoggedInUser());
        transactionRepository.save(transaction);
    }

    public void updateTransaction(UUID id, TransactionDto.Update transactionDto){
        Category category = categoryRepository.findById(transactionDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found!"));
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setType(transactionDto.getType());
        transaction.setCategory(category);
        transactionRepository.save(transaction);
    }

    public void deleteTransaction(UUID id){
        Transaction transaction = getTransactionById(id);
        transaction.setDeleted(true);
        transactionRepository.save(transaction);
    }
}

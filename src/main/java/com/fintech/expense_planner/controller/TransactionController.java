package com.fintech.expense_planner.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.expense_planner.dto.TransactionDto;
import com.fintech.expense_planner.mapper.TransactionMapper;
import com.fintech.expense_planner.model.Category;
import com.fintech.expense_planner.model.TransactionType;
import com.fintech.expense_planner.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService service;
    private final TransactionMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto.Response> getById(@PathVariable UUID id){
        return ResponseEntity.ok(mapper.toResponse(service.getTransactionById(id)));
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto.Response>> getAll(
        @RequestParam (required = false) TransactionType type,
        @RequestParam (required = false) Category category,
        @RequestParam (required = false) BigDecimal minAmount,
        @RequestParam (required = false) BigDecimal maxAmount,
        @RequestParam (required = false) String date
    ){
        return ResponseEntity.ok(mapper.toResponseList(service.getAllTransactions(type, category, minAmount, maxAmount, date)));
    }

    @PostMapping("/create")
    public void createTransaction(@RequestBody TransactionDto.Create transactionDto){
        service.createTransaction(transactionDto);
    }

    @PutMapping("/{id}")
    public void updateTransaction(@PathVariable UUID id, @RequestBody TransactionDto.Update transactionDto){
        service.updateTransaction(id, transactionDto);
    }

    @PutMapping("/remove/{id}")
    public void deleteTransaction(@PathVariable UUID id){
        service.deleteTransaction(id);
    }
}

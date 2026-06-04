package com.fintech.expense_planner.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.expense_planner.dto.BudgetDto;
import com.fintech.expense_planner.mapper.BudgetMapper;
import com.fintech.expense_planner.service.BudgetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/budgets")
public class BudgetController {
    private final BudgetService budgetService;
    private final BudgetMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDto.Response> getById(@PathVariable UUID id){
        return ResponseEntity.ok(mapper.toResponse(budgetService.getBudgetById(id)));
    }

    @GetMapping
    public ResponseEntity<List<BudgetDto.Response>> getAll(){
        return ResponseEntity.ok(mapper.toResponseList(budgetService.getAllBudgets()));
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<BudgetDto.Summary> getBudgetSummary(@PathVariable UUID id){
        return ResponseEntity.ok(budgetService.getSummary(id));
    }

    @PostMapping("/create")
    public void createBudget(@RequestBody BudgetDto.Create budgetDto){
        budgetService.createBudget(budgetDto);
    }

    @PutMapping("/{id}")
    public void updateBudget(@PathVariable UUID id, @RequestBody BudgetDto.Update budgetDto){
        budgetService.updateBudget(id, budgetDto);
    }

    @PutMapping("/remove/{id}")
    public void deleteBudget(@PathVariable UUID id){
        budgetService.deleteBudget(id);
    }
}

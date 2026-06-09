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

import com.fintech.expense_planner.dto.GoalDto;
import com.fintech.expense_planner.mapper.GoalMapper;
import com.fintech.expense_planner.service.GoalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goals")
public class GoalController {
    private final GoalService service;
    private final GoalMapper mapper;

    @GetMapping
    public ResponseEntity<List<GoalDto.Response>> getAllByUser(){
        return ResponseEntity.ok(mapper.toResponseList(service.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalDto.Response> getById(@PathVariable UUID id){
        return ResponseEntity.ok(mapper.toResponse(service.getById(id)));
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<GoalDto.Summary> getSummary(@PathVariable UUID id){
        return ResponseEntity.ok(service.getSummary(id));
    }

    @PostMapping("/create")
    public void createGoal(@RequestBody GoalDto.Create goalDto){
        service.createGoal(goalDto);
    }

    @PutMapping("/{id}")
    public void updateGoal(@PathVariable UUID id, @RequestBody GoalDto.Update goalDto){
        service.updateGoal(id, goalDto);
    }

    @PutMapping("/remove/{id}")
    public void deleteGoal(@PathVariable UUID id){
        service.deleteGoal(id);
    }
}
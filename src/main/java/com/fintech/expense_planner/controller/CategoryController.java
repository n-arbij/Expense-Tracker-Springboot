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

import com.fintech.expense_planner.dto.CategoryDto;
import com.fintech.expense_planner.mapper.CategoryMapper;
import com.fintech.expense_planner.model.Category;
import com.fintech.expense_planner.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService service;
    private final CategoryMapper mapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto.Response>> getAllCategories() {
        return ResponseEntity.ok(mapper.toResponseList(service.getAllCategories()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto.Response> getCategoryById(@PathVariable UUID id) {
        Category category = service.getCategoryById(id);
        return ResponseEntity.ok(mapper.toResponse(category));
    }

    @PostMapping("/create")
    public void createCategory(@RequestBody CategoryDto.Create categoryDto) {
        service.createCategory(categoryDto);
    }

    @PutMapping("/{id}")
    public void updateCategory(@PathVariable UUID id, @RequestBody CategoryDto.Update categoryDto){
        service.updateCategory(id, categoryDto);
    }

    @PutMapping("/remove/{id}")
    public void deleteCategory(@PathVariable UUID id){
        service.deleteCategory(id);
    }
}

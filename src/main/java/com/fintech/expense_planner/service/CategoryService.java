package com.fintech.expense_planner.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fintech.expense_planner.dto.CategoryDto;
import com.fintech.expense_planner.helper.Helper;
import com.fintech.expense_planner.model.Category;
import com.fintech.expense_planner.model.User;
import com.fintech.expense_planner.repository.CategoryRepository;
import com.fintech.expense_planner.specification.CategorySpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final Helper helper;

    public Category getCategoryById(UUID id) {
        User user = helper.getLoggedInUser();
        Category category = repository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        
        if(category.isDeleted()){
            throw new RuntimeException("Category not found");
        }
        if(!category.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Access denied");
        }
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public List<Category> getAllCategories() {
        Specification<Category> spec = CategorySpecification.belongsToUser(helper.getLoggedInUser())
            .and(CategorySpecification.isNotDeleted());

        return repository.findAll(spec);
    }

    public void createCategory(CategoryDto.Create categoryDto) {
        Category category = new Category();

        category.setUser(helper.getLoggedInUser());
        category.setName(categoryDto.getName());
        category.setColor(categoryDto.getColor());
        repository.save(category);
    }

    public void updateCategory(UUID id, CategoryDto.Update categoryDto) {
        Category category = repository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryDto.getName());
        category.setColor(categoryDto.getColor());
        repository.save(category);
    }

    public void deleteCategory(UUID id) 
    {
        Category category = getCategoryById(id);
        category.setDeleted(true);
        repository.save(category);
    }
}

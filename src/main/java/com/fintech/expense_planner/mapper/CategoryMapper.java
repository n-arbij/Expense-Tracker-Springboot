package com.fintech.expense_planner.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.fintech.expense_planner.dto.CategoryDto;
import com.fintech.expense_planner.model.Category;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    public CategoryDto.Response toResponse(Category category) {
        return new CategoryDto.Response(
            category.getId(),
            category.getName(),
            category.getColor()
        );
    }

    public List<CategoryDto.Response> toResponseList(List<Category> categories) {
        return categories.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

}

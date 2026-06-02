package com.fintech.expense_planner.specification;

import org.springframework.data.jpa.domain.Specification;

import com.fintech.expense_planner.model.Category;
import com.fintech.expense_planner.model.User;

public class CategorySpecification {
    public static Specification<Category >belongsToUser(User user){
        return (root, query, cb) -> cb.equal(root.get("user"), user);
    }

    public static Specification<Category> isNotDeleted(){
        return (root, query, cb) -> cb.equal(root.get("isDeleted"), false);
    }

}

package com.fintech.expense_planner.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.fintech.expense_planner.model.Category;
import com.fintech.expense_planner.model.User;


@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>, JpaSpecificationExecutor<Category>{
    List<Category> findAllByUser(User user);
}

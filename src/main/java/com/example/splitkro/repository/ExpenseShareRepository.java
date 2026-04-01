package com.example.splitkro.repository;

import com.example.splitkro.model.ExpenseShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, Long> {
    List<ExpenseShare> findByExpenseId(Long expenseId);

    List<ExpenseShare> findByUserId(Long userId);

    List<ExpenseShare> findByExpenseGroupId(Long groupId);
}

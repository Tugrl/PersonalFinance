package com.example.PersonalFinance.Logic;

import com.example.PersonalFinance.Entity.Budget;
import com.example.PersonalFinance.Repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class BudgetLogic {
    @Autowired
    private BudgetRepository budgetRepository;

    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }
    public Optional <Budget> getBudgetByUserId(UUID id) {
        return budgetRepository.findByUserId(id);
    }
    public void deductFromBudget(UUID userId, BigDecimal amount) {
        Budget budget = budgetRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Budget not found for user"));
        BigDecimal updatedBudget = budget.getBudget().subtract(amount);
        if (updatedBudget.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Not enough budget to deduct this amount.");
        }
        budget.setBudget(updatedBudget);
        budgetRepository.save(budget);
    }

}

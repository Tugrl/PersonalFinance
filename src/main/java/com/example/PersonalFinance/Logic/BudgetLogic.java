package com.example.PersonalFinance.Logic;

import com.example.PersonalFinance.Entity.Budget;
import com.example.PersonalFinance.Repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

}

package com.example.PersonalFinance.Logic;

import com.example.PersonalFinance.Entity.Budget;
import com.example.PersonalFinance.Entity.FinancialGoal;
import com.example.PersonalFinance.Entity.User;
import com.example.PersonalFinance.Repository.FinancialGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FinancialGoalLogic {

    @Autowired
    private FinancialGoalRepository financialGoalRepository;

    @Autowired
    private BudgetLogic budgetLogic;

    public FinancialGoal createFinancialGoal(FinancialGoal financialGoal, User user) {
        long monthsToSave = ChronoUnit.MONTHS.between(LocalDate.now(), financialGoal.getTargetDate());
        if (monthsToSave <= 0) {
            throw new IllegalArgumentException("Target date must be in the future.");
        }
        BigDecimal totalRemainingAmount = financialGoal.getTargetAmount().subtract(financialGoal.getSavedAmount());
        BigDecimal monthlySavings = totalRemainingAmount.divide(BigDecimal.valueOf(monthsToSave), BigDecimal.ROUND_HALF_UP);

        Budget userBudget = budgetLogic.getBudgetByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("Budget not found for user"));

        if (userBudget.getBudget().compareTo(monthlySavings) < 0) {
            throw new IllegalStateException("Not enough budget to save for this goal.");
        }

        budgetLogic.deductFromBudget(user.getId(), monthlySavings);
        financialGoal.setUser(user);
        return financialGoalRepository.save(financialGoal);
    }

    public List<FinancialGoal> getFinancialGoalsByUserId(UUID userId) {
        return financialGoalRepository.findAllByUserId(userId);
    }

    public Optional<FinancialGoal> getFinancialGoalById(UUID goalId) {
        return financialGoalRepository.findById(goalId);
    }
}

package com.example.PersonalFinance.Service;

import com.example.PersonalFinance.Dto.ExpenseDTO;
import com.example.PersonalFinance.Entity.User;
import com.example.PersonalFinance.Dto.IncomeDTO;
import com.example.PersonalFinance.Entity.Budget;
import com.example.PersonalFinance.Logic.BudgetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BudgetService {
    @Autowired
    BudgetLogic budgetLogic;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    public Budget calculateBudget(String username){
        List<IncomeDTO> incomes = incomeService.getAllIncomes(username);
        List<ExpenseDTO> expenses = expenseService.getAllExpenses(username);

        User user = (User) userService.loadUserByUsername(username);
        BigDecimal totalIncome = incomes.stream()
                .map(income -> new BigDecimal(income.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = expenses.stream()
                .map(expense -> new BigDecimal(expense.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalBudget= totalIncome.subtract(totalExpense);

        Budget budget = budgetLogic.getBudgetByUserId(user.getId()).orElse(new Budget());
        budget.setUser(user);
        budget.setTotalIncomes(totalIncome);
        budget.setTotalExpenses(totalExpense);
        budget.setBudget(totalBudget);
        budget.setBudgetDate(LocalDateTime.now());
        return budgetLogic.createBudget(budget);

    }
    public Budget getBudget(String username){
        User user = (User) userService.loadUserByUsername(username);
        return budgetLogic.getBudgetByUserId(user.getId()).orElse(null);

    }
}

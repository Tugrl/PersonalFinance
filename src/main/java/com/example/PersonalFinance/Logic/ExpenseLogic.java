package com.example.PersonalFinance.Logic;

import com.example.PersonalFinance.Entity.Expense;
import com.example.PersonalFinance.Repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ExpenseLogic {
    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense addExpense(Expense expense) {
    return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByUserId(UUID userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        System.out.println("Expenses found for user ID " + userId + ": " + expenses.size());
       // return expenseRepository.findAllUserById(userId);
        return expenses;
    }
    public Optional<Expense> getExpenseById(UUID expenseId) {
        return expenseRepository.findById(expenseId);
    }
}

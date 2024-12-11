package com.example.PersonalFinance.Service;

import com.example.PersonalFinance.Dto.ExpenseDTO;
import com.example.PersonalFinance.Dto.IncomeDTO;
import com.example.PersonalFinance.Entity.Expense;
import com.example.PersonalFinance.Logic.ExpenseLogic;
import com.example.PersonalFinance.Mapper.ExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.PersonalFinance.Entity.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

public class ExpenseService {
    @Autowired
    private ExpenseLogic expenseLogic;
    @Autowired
    private UserService userService;
    @Autowired
    private ExpenseMapper expenseMapper;

    public ExpenseDTO addExpense(ExpenseDTO expenseDTO, String username) {
        User user = (User) userService.loadUserByUsername(username);
        Expense expense = expenseMapper.toEntity(expenseDTO);
        expense.setUser(user);
        Expense savedExpense = expenseLogic.addExpense(expense);
        return expenseMapper.toDto(savedExpense);
    }
   // @PreAuthorize("hasAnyAuthority('READ_PRIVILEGE')")
    public List<ExpenseDTO> getAllExpenses(String username) {
        User user = (User) userService.loadUserByUsername(username);
        System.out.println("Listing expenses for user: " + user.getUsername() + " with ID: " + user.getId());

        List<Expense> expenses = expenseLogic.getExpensesByUserId(user.getId());
        return expenses.stream()
                .map(expenseMapper::toDto)
                .collect(Collectors.toList());
    }
    public Optional<ExpenseDTO> getExpenseById(UUID expenseId) {
        return expenseLogic.getExpenseById(expenseId).map(expenseMapper::toDto);
    }

}

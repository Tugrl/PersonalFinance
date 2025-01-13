package com.example.PersonalFinance.Service;

import com.example.PersonalFinance.Dto.ExpenseDTO;
import com.example.PersonalFinance.Dto.IncomeDTO;
import com.example.PersonalFinance.Dto.UserDTO;
import com.example.PersonalFinance.Entity.Expense;
import com.example.PersonalFinance.Logic.ExpenseLogic;
import com.example.PersonalFinance.Mapper.ExpenseMapper;
import com.example.PersonalFinance.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.PersonalFinance.Entity.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    @Autowired
    private UserMapper userMapper;

    public ExpenseDTO addExpense(ExpenseDTO expenseDTO, String username) {
       // User user = (User) userService.loadUserByUsername(username);
        UserDTO userDTO = userService.findByUsername(username);
        Expense expense = expenseMapper.toEntity(expenseDTO);
        User userExpense = userMapper.userDTOToUser(userDTO);
        expense.setUser(userExpense);
        Expense savedExpense = expenseLogic.addExpense(expense);
        return expenseMapper.toDto(savedExpense);
    }
    //@PreAuthorize("hasAnyAuthority('READ_PRIVILEGE')")
    public List<ExpenseDTO> getAllExpenses(String username) {
        User user = (User) userService.loadUserByUsername(username);
        UserDTO userDTO = userService.findByUsername(username);
        System.out.println("Listing expenses for user: " + userDTO.getUsername() + " with ID: " + userDTO.getId());

        List<Expense> expenses = expenseLogic.getExpensesByUserId(userDTO.getId());
        return expenses.stream()
                .map(expenseMapper::toDto)
                .collect(Collectors.toList());
    }
    public Optional<ExpenseDTO> getExpenseById(UUID expenseId) {
        return expenseLogic.getExpenseById(expenseId).map(expenseMapper::toDto);
    }

    public List<Map<String, Object>> getExpenseSummaries(String username) {
        UserDTO userDTO = userService.findByUsername(username);
        List<Expense> expenses = expenseLogic.getExpensesByUserId(userDTO.getId());
        return expenses.stream()
                .map(expense -> Map.of(
                        "id", (Object) expense.getId(),
                        "amount", (Object) expense.getAmount(),
                        "date",(Object) expense.getDate()
                ))
                .collect(Collectors.toList());

    }

}

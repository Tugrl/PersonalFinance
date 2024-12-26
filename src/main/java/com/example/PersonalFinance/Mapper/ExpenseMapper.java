package com.example.PersonalFinance.Mapper;

import com.example.PersonalFinance.Dto.ExpenseDTO;
import com.example.PersonalFinance.Entity.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {
    public ExpenseDTO toDto(Expense expense) {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setId(expense.getId());
        expenseDTO.setDate(expense.getDate());
        expenseDTO.setDescription(expense.getDescription());
        expenseDTO.setAmount(expense.getAmount());
        expenseDTO.setCategory(expense.getCategory());
        expenseDTO.setMandatory(expense.isMandatory());
        return expenseDTO;
    }
    public Expense toEntity(ExpenseDTO expenseDTO) {
        Expense expense = new Expense();
        expense.setId(expenseDTO.getId());
        expense.setDate(expenseDTO.getDate());
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setMandatory(expenseDTO.isMandatory());
        return expense;
    }
}

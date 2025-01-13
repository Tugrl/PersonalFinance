package com.example.PersonalFinance.Mapper;

import com.example.PersonalFinance.Dto.BudgetDTO;
import com.example.PersonalFinance.Dto.UserDTO;
import com.example.PersonalFinance.Entity.Budget;
import com.example.PersonalFinance.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class BudgetMapper {
    public BudgetDTO toDto(Budget budget, UserDTO userDTO) {

        BudgetDTO dto = new BudgetDTO();
        dto.setId(budget.getId());
        dto.setBudget(budget.getBudget());
        dto.setBudgetDate(budget.getBudgetDate());
        dto.setTotalExpenses(budget.getTotalExpenses());
        dto.setTotalIncomes(budget.getTotalIncomes());
        dto.setUser(userDTO);
        return dto;
    }
    public Budget toEntity(BudgetDTO dto, User user) {
        Budget budget = new Budget();
        budget.setId(dto.getId());
        budget.setBudgetDate(dto.getBudgetDate());
        budget.setBudget(dto.getBudget());
        budget.setTotalExpenses(dto.getTotalExpenses());
        budget.setTotalIncomes(dto.getTotalIncomes());
        budget.setUser(user);
        return budget;
    }
}

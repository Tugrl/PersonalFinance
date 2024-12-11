package com.example.PersonalFinance.Mapper;

import com.example.PersonalFinance.Dto.IncomeDTO;
import com.example.PersonalFinance.Entity.Income;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {
    public IncomeDTO toDto(Income income) {
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setId(income.getId());
        incomeDTO.setDate(income.getDate());
        incomeDTO.setAmount(income.getAmount());
        incomeDTO.setDescription(income.getDescription());
        return incomeDTO;
    }
    public Income toEntity(IncomeDTO incomeDTO) {
        Income income = new Income();
        income.setId(incomeDTO.getId());
        income.setDate(incomeDTO.getDate());
        income.setAmount(incomeDTO.getAmount());
        income.setDescription(incomeDTO.getDescription());
        return income;
    }
}

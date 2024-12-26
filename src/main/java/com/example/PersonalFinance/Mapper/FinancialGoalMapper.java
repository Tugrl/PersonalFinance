package com.example.PersonalFinance.Mapper;

import com.example.PersonalFinance.Dto.FinancialGoalDTO;
import com.example.PersonalFinance.Entity.FinancialGoal;
import org.springframework.stereotype.Component;

@Component
public class FinancialGoalMapper {
    public FinancialGoal toEntity(FinancialGoalDTO dto) {
        FinancialGoal entity = new FinancialGoal();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setTargetAmount(dto.getTargetAmount());
        entity.setSavedAmount(dto.getSavedAmount());
        entity.setTargetDate(dto.getTargetDate());
        return entity;
    }

    public FinancialGoalDTO toDto(FinancialGoal entity) {
        FinancialGoalDTO dto = new FinancialGoalDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setTargetAmount(entity.getTargetAmount());
        dto.setSavedAmount(entity.getSavedAmount());
        dto.setTargetDate(entity.getTargetDate());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}

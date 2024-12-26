package com.example.PersonalFinance.Service;

import com.example.PersonalFinance.Dto.FinancialGoalDTO;
import com.example.PersonalFinance.Entity.FinancialGoal;
import com.example.PersonalFinance.Entity.User;
import com.example.PersonalFinance.Logic.FinancialGoalLogic;
import com.example.PersonalFinance.Mapper.FinancialGoalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialGoalService {

    @Autowired
    private FinancialGoalLogic financialGoalLogic;

    @Autowired
    private UserService userService;

    @Autowired
    private FinancialGoalMapper financialGoalMapper;

    public FinancialGoalDTO createFinancialGoal(FinancialGoalDTO financialGoalDTO, String username) {
        User user = (User) userService.loadUserByUsername(username);
        FinancialGoal financialGoal = financialGoalMapper.toEntity(financialGoalDTO);

        FinancialGoal savedGoal = financialGoalLogic.createFinancialGoal(financialGoal, user);
        return financialGoalMapper.toDto(savedGoal);
    }

    public List<FinancialGoalDTO> getFinancialGoals(String username) {
        User user = (User) userService.loadUserByUsername(username);
        List<FinancialGoal> goals = financialGoalLogic.getFinancialGoalsByUserId(user.getId());
        return goals.stream()
                .map(financialGoalMapper::toDto)
                .collect(Collectors.toList());
    }
}

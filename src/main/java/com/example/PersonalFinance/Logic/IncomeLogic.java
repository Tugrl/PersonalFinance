package com.example.PersonalFinance.Logic;

import com.example.PersonalFinance.Entity.Income;
import com.example.PersonalFinance.Repository.IncomeRepository;
import com.example.PersonalFinance.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class IncomeLogic {
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private UserService userService;

    public Income addIncome(Income income) {
        return incomeRepository.save(income);
    }
    public List<Income> getIncomesByUserId(UUID userId) {
        return incomeRepository.findAllByUserId(userId);
    }
}

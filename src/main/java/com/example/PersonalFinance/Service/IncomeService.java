package com.example.PersonalFinance.Service;

import com.example.PersonalFinance.Dto.IncomeDTO;
import com.example.PersonalFinance.Entity.Income;
import com.example.PersonalFinance.Entity.User;
import com.example.PersonalFinance.Logic.IncomeLogic;
import com.example.PersonalFinance.Logic.UserLogic;
import com.example.PersonalFinance.Mapper.IncomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    @Autowired
    private IncomeLogic incomeLogic;
    @Autowired
    private IncomeMapper incomeMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserLogic userLogic;

   // @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public IncomeDTO addIncome(IncomeDTO incomeDTO, String username) {
    User user = (User) userService.loadUserByUsername(username);
    Income income = incomeMapper.toEntity(incomeDTO);
    income.setUser(user);
    Income savedIncome = incomeLogic.addIncome(income);
        return incomeMapper.toDto(savedIncome);
    }

    public List<IncomeDTO> getAllIncomes(String username) {
      User user = (User) userService.loadUserByUsername(username);
      List<Income> incomes =incomeLogic.getIncomesByUserId(user.getId());
      return incomes.stream().map(incomeMapper::toDto).collect(Collectors.toList());
    }

}

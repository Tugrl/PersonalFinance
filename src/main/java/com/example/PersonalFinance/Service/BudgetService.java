package com.example.PersonalFinance.Service;

import com.example.PersonalFinance.Dto.BudgetDTO;
import com.example.PersonalFinance.Dto.ExpenseDTO;
import com.example.PersonalFinance.Dto.UserDTO;
import com.example.PersonalFinance.Entity.User;
import com.example.PersonalFinance.Dto.IncomeDTO;
import com.example.PersonalFinance.Entity.Budget;
import com.example.PersonalFinance.Logic.BudgetLogic;
import com.example.PersonalFinance.Mapper.BudgetMapper;
import com.example.PersonalFinance.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

//@Component
//@Service
//public class BudgetService {
//    @Autowired
//    BudgetLogic budgetLogic;
//    @Autowired
//    private IncomeService incomeService;
//    @Autowired
//    private ExpenseService expenseService;
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private UserMapper userMapper;
//
//    public Budget calculateBudget(String username){
//        List<IncomeDTO> incomes = incomeService.getAllIncomes(username);
//        List<ExpenseDTO> expenses = expenseService.getAllExpenses(username);
//
//        //User user = (User) userService.loadUserByUsername(username);
//
//        UserDTO userDTO = userService.findByUsername(username);
//        User userBudget = userMapper.userDTOToUser(userDTO);
//
//        BigDecimal totalIncome = incomes.stream()
//                .map(income -> new BigDecimal(income.getAmount()))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        BigDecimal totalExpense = expenses.stream()
//                .map(expense -> new BigDecimal(expense.getAmount()))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        BigDecimal totalBudget= totalIncome.subtract(totalExpense);
//
//        Budget budget = budgetLogic.getBudgetByUserId(userBudget.getId()).orElse(new Budget());
//        budget.setUser(userBudget);
//        budget.setTotalIncomes(totalIncome);
//        budget.setTotalExpenses(totalExpense);
//        budget.setBudget(totalBudget);
//        budget.setBudgetDate(LocalDateTime.now());
//        return budgetLogic.createBudget(budget);
//
//    }
//    public Budget getBudget(String username){
//        User user = (User) userService.loadUserByUsername(username);
//        return budgetLogic.getBudgetByUserId(user.getId()).orElse(null);
//
//    }
//}
@Service
public class BudgetService {

    @Autowired
    private BudgetLogic budgetLogic;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BudgetMapper budgetMapper;

    public BudgetDTO calculateBudget(String username) {
        List<IncomeDTO> incomes = incomeService.getAllIncomes(username);
        List<ExpenseDTO> expenses = expenseService.getAllExpenses(username);

        UserDTO userDTO = userService.findByUsername(username);
        User userBudget = userMapper.userDTOToUser(userDTO);

        BigDecimal totalIncome = incomes.stream()
                .map(income -> new BigDecimal(income.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = expenses.stream()
                .map(expense -> new BigDecimal(expense.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalBudget = totalIncome.subtract(totalExpense);

        Budget budget = budgetLogic.getBudgetByUserId(userBudget.getId()).orElse(new Budget());
        budget.setUser(userBudget);
        budget.setTotalIncomes(totalIncome);
        budget.setTotalExpenses(totalExpense);
        budget.setBudget(totalBudget);
        budget.setBudgetDate(LocalDateTime.now());
        budgetLogic.createBudget(budget);

        return budgetMapper.toDto(budget, userDTO);
    }

    public BudgetDTO getBudget(String username) {
        User user = (User) userService.loadUserByUsername(username);
        Budget budget = budgetLogic.getBudgetByUserId(user.getId()).orElse(null);

        if (budget == null) {
            return null;
        }

        UserDTO userDTO = userMapper.userToUserDTO(user);
        return budgetMapper.toDto(budget, userDTO);

    }
    public Map<String,Object> fetchBudget(String username) {
        User user = (User) userService.loadUserByUsername(username);
        Budget budget = budgetLogic.getBudgetByUserId(user.getId()).orElse(null);
        UserDTO userDTO = userMapper.userToUserDTO(user);
        BudgetDTO budgetDTO = budgetMapper.toDto(budget, userDTO);

        return Map.of(

                "totalExpense", budgetDTO.getTotalExpenses(),
                "totalIncome", budgetDTO.getTotalIncomes(),
                "budget", budgetDTO.getBudget(),
                "name",  userDTO.getName(),
                "surname", userDTO.getSurname()
        );
    }
}


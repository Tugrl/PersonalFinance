package com.example.PersonalFinance.Service;

import com.example.PersonalFinance.Dto.BudgetDTO;
import com.example.PersonalFinance.Dto.ExpenseDTO;
import com.example.PersonalFinance.Dto.FinancialGoalDTO;
import com.example.PersonalFinance.Dto.IncomeDTO;
import com.example.PersonalFinance.Entity.Budget;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReportService {

    public BigDecimal calculateTotalAmount(Stream<BigDecimal> amounts) {
        return amounts.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateMonthlySavingsRequired(BigDecimal remainingAmount, long monthsLeft) {
        return remainingAmount.divide(
                BigDecimal.valueOf(Math.max(monthsLeft, 1)),
                RoundingMode.HALF_UP
        );
    }

    public BigDecimal calculateTotalMonthlySavingsRequired(List<FinancialGoalDTO> goals) {
        return goals.stream()
                .map(goal -> calculateMonthlySavingsRequired(
                        goal.getTargetAmount().subtract(goal.getSavedAmount()),
                        ChronoUnit.MONTHS.between(LocalDate.now(), goal.getTargetDate())
                ))
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<String, Object> generateMonthlyReportData(
            List<IncomeDTO> incomes,
            List<ExpenseDTO> expenses,
            List<FinancialGoalDTO> goals,
            BudgetDTO budget
    ) {
        BigDecimal totalIncome = calculateTotalAmount(
                incomes.stream().map(income -> BigDecimal.valueOf(income.getAmount()))
        );

        BigDecimal totalExpense = calculateTotalAmount(
                expenses.stream().map(expense -> BigDecimal.valueOf(expense.getAmount()))
        );

        BigDecimal netBudget = totalIncome.subtract(totalExpense);
        BigDecimal totalSavedAmount = calculateTotalAmount(goals.stream().map(FinancialGoalDTO::getSavedAmount));
        BigDecimal totalMonthlySavingsRequired = calculateTotalMonthlySavingsRequired(goals);
        BigDecimal remainingBudget = netBudget.subtract(totalMonthlySavingsRequired);

        List<Map<String, Object>> goalReports = goals.stream().map(goal -> {
            BigDecimal remainingAmount = goal.getTargetAmount().subtract(goal.getSavedAmount());
            long monthsLeft = ChronoUnit.MONTHS.between(LocalDate.now(), goal.getTargetDate());
            BigDecimal monthlySavingsRequired = calculateMonthlySavingsRequired(remainingAmount, monthsLeft);

            Map<String, Object> report = new HashMap<>();
            report.put("description", goal.getDescription());
            report.put("targetAmount", goal.getTargetAmount());
            report.put("savedAmount", goal.getSavedAmount());
            report.put("remainingAmount", remainingAmount);
            report.put("monthsLeft", monthsLeft);
            report.put("monthlySavingsRequired", monthlySavingsRequired);
            return report;
        }).collect(Collectors.toList());

        return Map.of(
                "month", LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear(),
                "totalIncome", totalIncome,
                "totalExpense", totalExpense,
                "netBudget", netBudget,
                "financialGoals", goalReports,
                "remainingBudget", remainingBudget,
                "totalMonthlySavingsRequired", totalMonthlySavingsRequired
        );
    }
}

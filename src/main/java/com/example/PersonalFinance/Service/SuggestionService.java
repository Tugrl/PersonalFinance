package com.example.PersonalFinance.Service;

import com.example.PersonalFinance.Dto.ExpenseDTO;
import com.example.PersonalFinance.Dto.FinancialGoalDTO;
import com.example.PersonalFinance.Dto.IncomeDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SuggestionService {

    public BigDecimal calculateTotalAmount(Stream<BigDecimal> amounts) {
        return amounts.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
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

    private BigDecimal calculateMonthlySavingsRequired(BigDecimal remainingAmount, long monthsLeft) {
        return remainingAmount.divide(
                BigDecimal.valueOf(Math.max(monthsLeft, 1)),
                RoundingMode.HALF_UP
        );
    }

    private String normalizeCategory(String category) {
        if (category == null) {
            return "unknown"; // Kategori yoksa "unknown" olarak işaretle
        }
        return category.trim().toLowerCase().replaceAll("[^a-z0-9]", " "); // Harf, rakam dışındaki karakterleri boşluk ile değiştir
    }

    public Map<String, Object> generateSavingSuggestions(
            List<IncomeDTO> incomes,
            List<ExpenseDTO> expenses,
            List<FinancialGoalDTO> goals
    ) {
        BigDecimal totalIncome = calculateTotalAmount(
                incomes.stream().map(income -> BigDecimal.valueOf(income.getAmount()))
        );
        BigDecimal totalExpense = calculateTotalAmount(
                expenses.stream().map(expense -> BigDecimal.valueOf(expense.getAmount()))
        );

        Map<String, BigDecimal> nonMandatoryExpensesByCategory = expenses.stream()
                .filter(expense -> !expense.isMandatory())
                .collect(Collectors.groupingBy(
                        expense -> normalizeCategory(expense.getCategory()),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                expense -> BigDecimal.valueOf(expense.getAmount()),
                                BigDecimal::add
                        )
                ));

        BigDecimal threshold = totalIncome.multiply(BigDecimal.valueOf(0.2));
        List<Map<String, Object>> suggestions = new ArrayList<>();
        nonMandatoryExpensesByCategory.forEach((category, amount) -> {
            if (amount.compareTo(threshold) > 0) {
                BigDecimal potentialSavings = amount.multiply(BigDecimal.valueOf(0.2));
                BigDecimal recommendingSaving = amount.subtract(potentialSavings);
                Map<String, Object> suggestion = Map.of(
                        "category", category,
                        "currentSpending", amount,
                        "recommendedSpending", recommendingSaving,
                        "potentialSavings", potentialSavings
                );
                suggestions.add(suggestion);
            }
        });


        List<String> expenseDescriptions = expenses.stream()
                .filter(expense -> !expense.isMandatory())
                .filter(expense -> BigDecimal.valueOf(expense.getAmount()).compareTo(threshold) > 0)
                .map(ExpenseDTO::getDescription)
                .collect(Collectors.toList());

        BigDecimal totalMonthlySavingsRequired = calculateTotalMonthlySavingsRequired(goals);
        BigDecimal possibleSavings = totalIncome.subtract(totalExpense);

        if (possibleSavings.compareTo(totalMonthlySavingsRequired) < 0) {
            suggestions.add(Map.of(

                    "requiredSavings", totalMonthlySavingsRequired,
                    "currentSavingsPotential", possibleSavings
                    //"expenseDescription", expenseDescriptions
            ));
        } //else {
//            suggestions.add(Map.of(
//
//                    "expenseDescription", expenseDescriptions
//            ));
//        }

        return Map.of(
                "totalIncome", totalIncome,
                "totalExpense", totalExpense,
                "suggestions", suggestions
        );
    }
}

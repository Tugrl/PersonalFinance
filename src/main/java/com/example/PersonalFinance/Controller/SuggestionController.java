//package com.example.PersonalFinance.Controller;
//
//import com.example.PersonalFinance.Dto.ExpenseDTO;
//import com.example.PersonalFinance.Dto.FinancialGoalDTO;
//import com.example.PersonalFinance.Dto.IncomeDTO;
//import com.example.PersonalFinance.Service.ExpenseService;
//import com.example.PersonalFinance.Service.FinancialGoalService;
//import com.example.PersonalFinance.Service.IncomeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.security.Principal;
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@RestController
//@RequestMapping("/api/suggestions")
//public class SuggestionController {
//    @Autowired
//    private IncomeService incomeService;
//    @Autowired
//    private ExpenseService expenseService;
//    @Autowired
//    private FinancialGoalService financialGoalService;
//
//    @GetMapping
//    public ResponseEntity<?> getSavingSuggestions(Principal principal) {
//        String username = principal.getName();
//
//        List<IncomeDTO> incomes = incomeService.getAllIncomes(username);
//        List<ExpenseDTO> expenses = expenseService.getAllExpenses(username);
//        List<FinancialGoalDTO> goals = financialGoalService.getFinancialGoals(username);
//
//        BigDecimal totalIncome = calculateTotalAmount(
//                incomes.stream().map(income -> BigDecimal.valueOf(income.getAmount()))
//        );
//        BigDecimal totalExpense = calculateTotalAmount(
//                expenses.stream().map(expense -> BigDecimal.valueOf(expense.getAmount()))
//        );
//
//        // Zorunlu olmayan harcamaları kategoriye göre gruplama
//        Map<String, BigDecimal> nonMandatoryExpensesByCategory = expenses.stream()
//                .filter(expense -> !expense.isMandatory()) // Zorunlu olmayan harcamaları filtrele
//                .collect(Collectors.groupingBy(
//                        expense -> normalizeCategory(expense.getCategory()),
//                        Collectors.reducing(
//                                BigDecimal.ZERO,
//                                expense -> BigDecimal.valueOf(expense.getAmount()),
//                                BigDecimal::add
//                        )
//                ));
//
//        // Harcama açıklamalarını toplama
//
////        BigDecimal threshold = totalIncome.multiply(BigDecimal.valueOf(0.2));
////        List<Map<String, Object>> suggestions = new ArrayList<>();
////        nonMandatoryExpensesByCategory.forEach((category, amount) -> {
////             // Gelirin %20'si
////            if (amount.compareTo(threshold) > 0) {
////                BigDecimal potentialSavings = amount.subtract(threshold);
////                Map<String, Object> suggestion = Map.of(
////                        "category", category,
////                        "currentSpending", amount,
////                        "recommendedSpending", threshold,
////                        "potentialSavings", potentialSavings
////                      //  "expenseDescription",expenseDescriptions
////                );
////                suggestions.add(suggestion);
////            }
////        });
//        BigDecimal threshold = totalIncome.multiply(BigDecimal.valueOf(0.2));
//        List<Map<String, Object>> suggestions = new ArrayList<>();
//        nonMandatoryExpensesByCategory.forEach((category, amount) -> {
//            if (amount.compareTo(threshold) > 0) {
//                // Fazlalığın sadece %20'si alınacak
//                BigDecimal potentialSavings = amount.subtract(threshold).multiply(BigDecimal.valueOf(0.2));
//                BigDecimal recommendingSaving2=amount.subtract(potentialSavings);
//                Map<String, Object> suggestion = Map.of(
//                        "category", category,
//                        "currentSpending", amount,
//                        "recommendedSpending", recommendingSaving2,
//                        "potentialSavings", potentialSavings
//                );
//                suggestions.add(suggestion);
//            }
//        });
//
//        List<String> expenseDescriptions = expenses.stream()
//                .filter(expense -> !expense.isMandatory()) // Zorunlu olmayan harcamaları filtrele
//                .filter(expense -> BigDecimal.valueOf(expense.getAmount()).compareTo(threshold) > 0) // Gelirin %20'sinden fazla harcamaları filtrele
//                .map(ExpenseDTO::getDescription)
//                .collect(Collectors.toList());
//
//        BigDecimal totalMonthlySavingsRequired = calculateTotalMonthlySavingsRequired(goals);
//        BigDecimal possibleSavings = totalIncome.subtract(totalExpense);
//        if (possibleSavings.compareTo(totalMonthlySavingsRequired) < 0) {
//            suggestions.add(Map.of(
//                    "message", "Hedeflerinize ulaşmak için harcamalarınızı azaltmanız gerekiyor.",
//                    "requiredSavings", totalMonthlySavingsRequired,
//                    "currentSavingsPotential", possibleSavings,
//                    "expenseDescription", expenseDescriptions
//            ));
//        } else {
//            suggestions.add(Map.of(
//                    "message", "Hedeflerinize ulaşmak için yeterli bütçeniz mevcut. Daha fazla tasarruf yapmayı düşünebilirsiniz!",
//                    "expenseDescription", expenseDescriptions
//            ));
//        }
//
//        // Sonuç döndür
//        return ResponseEntity.ok(Map.of(
//                "totalIncome", totalIncome,
//                "totalExpense", totalExpense,
//                "suggestions", suggestions
//        ));
//    }
//
//    private BigDecimal calculateTotalAmount(Stream<BigDecimal> amounts) {
//        return amounts.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    private BigDecimal calculateTotalMonthlySavingsRequired(List<FinancialGoalDTO> goals) {
//        return goals.stream()
//                .map(goal -> calculateMonthlySavingsRequired(
//                        goal.getTargetAmount().subtract(goal.getSavedAmount()),
//                        ChronoUnit.MONTHS.between(LocalDate.now(), goal.getTargetDate())
//                ))
//                .filter(Objects::nonNull)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    private BigDecimal calculateMonthlySavingsRequired(BigDecimal remainingAmount, long monthsLeft) {
//        return remainingAmount.divide(
//                BigDecimal.valueOf(Math.max(monthsLeft, 1)),
//                RoundingMode.HALF_UP
//        );
//    }
//
//    private String normalizeCategory(String category) {
//        if (category == null) {
//            return "unknown"; // Kategori yoksa "unknown" olarak işaretle
//        }
//        return category.trim().toLowerCase().replaceAll("[^a-z0-9]", " "); // Harf, rakam dışındaki karakterleri boşluk ile değiştir
//    }
//}

package com.example.PersonalFinance.Controller;

import com.example.PersonalFinance.Dto.ExpenseDTO;
import com.example.PersonalFinance.Dto.FinancialGoalDTO;
import com.example.PersonalFinance.Dto.IncomeDTO;
import com.example.PersonalFinance.Service.ExpenseService;
import com.example.PersonalFinance.Service.FinancialGoalService;
import com.example.PersonalFinance.Service.IncomeService;
import com.example.PersonalFinance.Service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private FinancialGoalService financialGoalService;

    @Autowired
    private SuggestionService suggestionService;

    @GetMapping
    public ResponseEntity<?> getSavingSuggestions(Principal principal) {
        String username = principal.getName();

        List<IncomeDTO> incomes = incomeService.getAllIncomes(username);
        List<ExpenseDTO> expenses = expenseService.getAllExpenses(username);
        List<FinancialGoalDTO> goals = financialGoalService.getFinancialGoals(username);

        Map<String, Object> suggestions = suggestionService.generateSavingSuggestions(incomes, expenses, goals);
        return ResponseEntity.ok(suggestions);
    }
}


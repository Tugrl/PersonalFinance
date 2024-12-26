////package com.example.PersonalFinance.Controller;
////
////import com.example.PersonalFinance.Dto.ExpenseDTO;
////import com.example.PersonalFinance.Dto.FinancialGoalDTO;
////import com.example.PersonalFinance.Entity.FinancialGoal;
////import com.example.PersonalFinance.Dto.IncomeDTO;
////import com.example.PersonalFinance.Entity.Budget;
////import com.example.PersonalFinance.Service.BudgetService;
////import com.example.PersonalFinance.Service.ExpenseService;
////import com.example.PersonalFinance.Service.FinancialGoalService;
////import com.example.PersonalFinance.Service.IncomeService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.GetMapping;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RestController;
////
////import java.math.BigDecimal;
////import java.math.RoundingMode;
////import java.security.Principal;
////import java.time.LocalDate;
////import java.time.temporal.ChronoUnit;
////import java.util.HashMap;
////import java.util.List;
////import java.util.Map;
////import java.util.Objects;
////import java.util.stream.Collectors;
////
////@RestController
////@RequestMapping("/api/reports")
////public class ReportController {
////
////    @Autowired
////    private IncomeService incomeService;
////
////    @Autowired
////    private ExpenseService expenseService;
////
////    @Autowired
////    private FinancialGoalService financialGoalService;
////    @Autowired
////    private BudgetService budgetService;
////
////    @GetMapping("/monthly")
////    public ResponseEntity<?> generateMonthlyReport(Principal principal) {
////        String username = principal.getName();
////        List<IncomeDTO> incomes = incomeService.getAllIncomes(username);
////        List<ExpenseDTO> expenses = expenseService.getAllExpenses(username);
////        List<FinancialGoalDTO> goals = financialGoalService.getFinancialGoals(username);
////
////
////        BigDecimal totalIncome = incomes.stream()
////                .map(income -> new BigDecimal(income.getAmount()))
////                .reduce(BigDecimal.ZERO, BigDecimal::add);
////
////        BigDecimal totalExpense = expenses.stream()
////                .map(expense -> new BigDecimal(expense.getAmount()))
////                .reduce(BigDecimal.ZERO, BigDecimal::add);
////
////        BigDecimal netBudget = totalIncome.subtract(totalExpense);
////
////        BigDecimal totalSavedAmount = goals.stream()
////                 .map(FinancialGoalDTO::getSavedAmount)
////                .filter(Objects::nonNull)
////                .reduce(BigDecimal.ZERO, BigDecimal::add);
////
////
////        List<Map<String, Object>> goalReports = goals.stream().map(goal -> {
////            BigDecimal remainingAmount = goal.getTargetAmount().subtract(goal.getSavedAmount());
////            long monthsLeft = ChronoUnit.MONTHS.between(LocalDate.now(), goal.getTargetDate());
////            BigDecimal monthlySavingsRequired = remainingAmount.divide(
////                    BigDecimal.valueOf(Math.max(monthsLeft, 1)),
////                    RoundingMode.HALF_UP
////            );
////
////            BigDecimal totalMonthlySavingsRequired = goals.stream()
////                    .map(goal2 -> {
////                        BigDecimal netRemaining = goal2.getTargetAmount().subtract(goal2.getSavedAmount());
////                        long monthsLefts = ChronoUnit.MONTHS.between(LocalDate.now(), goal2.getTargetDate());
////                        return netRemaining.divide(
////                                BigDecimal.valueOf(Math.max(monthsLefts, 1)),
////                                RoundingMode.HALF_UP
////                        );
////                    })
////                    .filter(Objects::nonNull) // Null olmayan değerleri al
////                    .reduce(BigDecimal.ZERO, BigDecimal::add); // Toplamını al
////
////           // System.out.println("Toplam Gerekli Aylık Birikim: " + totalMonthlySavingsRequired);
////
////            Budget budgets = budgetService.getBudget(username);
////            BigDecimal totalBudget = budgets.getBudget();
////            BigDecimal totalRemaining = totalBudget.subtract(totalMonthlySavingsRequired);
////
////
////            Map<String, Object> report = new HashMap<>();
////            report.put("description", goal.getDescription());
////            report.put("targetAmount", goal.getTargetAmount());
////            report.put("savedAmount", goal.getSavedAmount());
////            report.put("remainingAmount", remainingAmount);
////            report.put("monthsLeft", monthsLeft);
////            report.put("monthlySavingsRequired", monthlySavingsRequired);
////
////            return report;
////        }).collect(Collectors.toList());
////        Map<String, Object> report = Map.of(
////                "month", LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear(),
////                "totalIncome", totalIncome,
////                "totalExpense", totalExpense,
////                "netBudget", netBudget,
////                "financialGoals", goalReports,
////                "remainingBudget",totalBudget,
////                "totalSavedAmount",totalSavedAmount
////        );
////
////        return ResponseEntity.ok(report);
////    }
////}
////
//package com.example.PersonalFinance.Controller;
//
//import com.example.PersonalFinance.Dto.ExpenseDTO;
//import com.example.PersonalFinance.Dto.FinancialGoalDTO;
//import com.example.PersonalFinance.Dto.IncomeDTO;
//import com.example.PersonalFinance.Entity.Budget;
//import com.example.PersonalFinance.Service.BudgetService;
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
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@RestController
//@RequestMapping("/api/reports")
//public class ReportController {
//
//    @Autowired
//    private IncomeService incomeService;
//
//    @Autowired
//    private ExpenseService expenseService;
//
//    @Autowired
//    private FinancialGoalService financialGoalService;
//
//    @Autowired
//    private BudgetService budgetService;
//
//    @GetMapping("/monthly")
//    public ResponseEntity<?> generateMonthlyReport(Principal principal) {
//        String username = principal.getName();
//
//        List<IncomeDTO> incomes = incomeService.getAllIncomes(username);
//        List<ExpenseDTO> expenses = expenseService.getAllExpenses(username);
//        List<FinancialGoalDTO> goals = financialGoalService.getFinancialGoals(username);
//        Budget budget = budgetService.getBudget(username);
//
//        BigDecimal totalIncome = calculateTotalAmount(
//                incomes.stream().map(income -> BigDecimal.valueOf(income.getAmount()))
//        );
//
//        BigDecimal totalExpense = calculateTotalAmount(
//                expenses.stream().map(expense -> BigDecimal.valueOf(expense.getAmount()))
//        );
//
//        BigDecimal netBudget = totalIncome.subtract(totalExpense);
//        BigDecimal totalSavedAmount = calculateTotalAmount(goals.stream().map(FinancialGoalDTO::getSavedAmount));
//        BigDecimal totalMonthlySavingsRequired = calculateTotalMonthlySavingsRequired(goals);
//
//        BigDecimal remainingBudget = netBudget.subtract(totalMonthlySavingsRequired);
//
//        // Generate reports for each goal
//        List<Map<String, Object>> goalReports = goals.stream().map(goal -> {
//            BigDecimal remainingAmount = goal.getTargetAmount().subtract(goal.getSavedAmount());
//            long monthsLeft = ChronoUnit.MONTHS.between(LocalDate.now(), goal.getTargetDate());
//            BigDecimal monthlySavingsRequired = calculateMonthlySavingsRequired(remainingAmount, monthsLeft);
//
//            Map<String, Object> report = new HashMap<>();
//            report.put("description", goal.getDescription());
//            report.put("targetAmount", goal.getTargetAmount());
//            report.put("savedAmount", goal.getSavedAmount());
//            report.put("remainingAmount", remainingAmount);
//            report.put("monthsLeft", monthsLeft);
//            report.put("monthlySavingsRequired", monthlySavingsRequired);
//
//            return report;
//        }).collect(Collectors.toList());
//
//        // Final report
//        Map<String, Object> report = Map.of(
//                "month", LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear(),
//                "totalIncome", totalIncome,
//                "totalExpense", totalExpense,
//                "netBudget", netBudget,
//                "financialGoals", goalReports,
//                "remainingBudget", remainingBudget,
//               // "totalSavedAmount", totalSavedAmount,
//                "totalMonthlySavingsRequired", totalMonthlySavingsRequired
//        );
//
//        return ResponseEntity.ok(report);
//    }
//
//    private BigDecimal calculateTotalAmount(Stream<BigDecimal> amounts) {
//        return amounts.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    private BigDecimal calculateMonthlySavingsRequired(BigDecimal remainingAmount, long monthsLeft) {
//        return remainingAmount.divide(
//                BigDecimal.valueOf(Math.max(monthsLeft, 1)),
//                RoundingMode.HALF_UP
//        );
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
//}
package com.example.PersonalFinance.Controller;

import com.example.PersonalFinance.Dto.BudgetDTO;
import com.example.PersonalFinance.Dto.ExpenseDTO;
import com.example.PersonalFinance.Dto.FinancialGoalDTO;
import com.example.PersonalFinance.Dto.IncomeDTO;
import com.example.PersonalFinance.Entity.Budget;
import com.example.PersonalFinance.Service.BudgetService;
import com.example.PersonalFinance.Service.ExpenseService;
import com.example.PersonalFinance.Service.FinancialGoalService;
import com.example.PersonalFinance.Service.IncomeService;
import com.example.PersonalFinance.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private FinancialGoalService financialGoalService;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/monthly")
    public ResponseEntity<?> generateMonthlyReport(Principal principal) {
        String username = principal.getName();

        List<IncomeDTO> incomes = incomeService.getAllIncomes(username);
        List<ExpenseDTO> expenses = expenseService.getAllExpenses(username);
        List<FinancialGoalDTO> goals = financialGoalService.getFinancialGoals(username);
        BudgetDTO budget = budgetService.getBudget(username);

        Map<String, Object> report = reportService.generateMonthlyReportData(incomes, expenses, goals, budget);
        return ResponseEntity.ok(report);
    }
}


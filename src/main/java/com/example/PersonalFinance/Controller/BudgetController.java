package com.example.PersonalFinance.Controller;

import com.example.PersonalFinance.Dto.BudgetDTO;
import com.example.PersonalFinance.Entity.Budget;
import com.example.PersonalFinance.Service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    //@GetMapping
//    public ResponseEntity<BigDecimal> getBudget(Principal principal) {
//        String username = principal.getName();
//        BigDecimal budget = budgetService.calculateBudget(username);
//        return ResponseEntity.ok(budget);
//    }
//    @GetMapping
//    public ResponseEntity<BudgetDTO> getBudget(Principal principal) {
//        String username = principal.getName();
//        BudgetDTO budget = budgetService.getBudget(username);
//        return ResponseEntity.ok(budget);
//    }
    @PostMapping
    public ResponseEntity<String> createBudget(Principal principal) {
        String username = principal.getName();
        BudgetDTO budget = budgetService.calculateBudget(username);
        return ResponseEntity.ok("Bütçe Başarıyla Oluşturuldu");
    }
    @GetMapping
    public ResponseEntity<?> fetchBudget(Principal principal) {
        String username = principal.getName();
        Map<String,Object> budgets = budgetService.fetchBudget(username);
        return ResponseEntity.ok(budgets);
    }

}

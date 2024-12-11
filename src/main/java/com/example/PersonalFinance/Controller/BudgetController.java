package com.example.PersonalFinance.Controller;

import com.example.PersonalFinance.Entity.Budget;
import com.example.PersonalFinance.Service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

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
    @GetMapping
    public ResponseEntity<Budget> getBudget(Principal principal) {
        String username = principal.getName();
        Budget budget = budgetService.getBudget(username);
        return ResponseEntity.ok(budget);
    }
    @PostMapping("/calculate")
    public ResponseEntity<Budget> createBudget(Principal principal) {
        String username = principal.getName();
        Budget budget = budgetService.calculateBudget(username);
        return ResponseEntity.ok(budget);
    }

}

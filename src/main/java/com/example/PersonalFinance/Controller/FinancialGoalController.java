package com.example.PersonalFinance.Controller;

import com.example.PersonalFinance.Dto.FinancialGoalDTO;
import com.example.PersonalFinance.Service.FinancialGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/financial-goals")
public class FinancialGoalController {
    @Autowired
    private FinancialGoalService financialGoalService;

    @PostMapping
    public ResponseEntity<FinancialGoalDTO> createFinancialGoal(@RequestBody FinancialGoalDTO financialGoalDTO, Principal principal) {
        String username = principal.getName();
        FinancialGoalDTO createdGoal = financialGoalService.createFinancialGoal(financialGoalDTO, username);
        return ResponseEntity.ok(createdGoal);
    }

    @GetMapping
    public ResponseEntity<List<FinancialGoalDTO>> getFinancialGoals(Principal principal) {
        String username = principal.getName();
        List<FinancialGoalDTO> goals = financialGoalService.getFinancialGoals(username);
        return ResponseEntity.ok(goals);
    }
}

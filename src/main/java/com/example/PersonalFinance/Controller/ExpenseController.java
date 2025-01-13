package com.example.PersonalFinance.Controller;

import com.example.PersonalFinance.Dto.ExpenseDTO;
import com.example.PersonalFinance.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> addExpense(@RequestBody ExpenseDTO expenseDTO, Principal principal) {
        try{
            String username = principal.getName();
            ExpenseDTO savedExpense= expenseService.addExpense(expenseDTO, username);
            return ResponseEntity.ok(savedExpense);
        }
        catch(Exception e){
            return ResponseEntity.status(403).body(null);
        }
    }

@GetMapping
public ResponseEntity<List<Map<String, Object>>> getAllExpenses(Principal principal) {
    try{
        String username = principal.getName();
        List<Map<String, Object>> expenses = expenseService.getExpenseSummaries(username);
        return ResponseEntity.ok(expenses);
    }
    catch(Exception e){
        return ResponseEntity.status(403).body(null);
    }
}
    @GetMapping("/{expenseId}")
    public ResponseEntity<Optional<ExpenseDTO>> getExpenseById(@PathVariable("expenseId") UUID expenseId) {
        try{
            return ResponseEntity.ok(expenseService.getExpenseById(expenseId));
        }
        catch(Exception e){
            return ResponseEntity.status(403).body(null);
        }
    }
}

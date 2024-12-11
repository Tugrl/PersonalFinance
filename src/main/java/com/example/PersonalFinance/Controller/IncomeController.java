package com.example.PersonalFinance.Controller;

import com.example.PersonalFinance.Dto.IncomeDTO;
import com.example.PersonalFinance.Service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
    @Autowired
    private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<IncomeDTO> addIncome(@RequestBody IncomeDTO incomeDTO, Principal principal) {
        String username = principal.getName();
        IncomeDTO savedIncome = incomeService.addIncome(incomeDTO, username);
        return ResponseEntity.ok(savedIncome);
    }
    @GetMapping
    public ResponseEntity<List<IncomeDTO>> getAllIncome(Principal principal) {
        String username = principal.getName();
        List<IncomeDTO> incomes = incomeService.getAllIncomes(username);
        return ResponseEntity.ok(incomes);
    }
}

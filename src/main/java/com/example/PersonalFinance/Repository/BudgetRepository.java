package com.example.PersonalFinance.Repository;

import com.example.PersonalFinance.Entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    Optional <Budget> findByUserId(UUID id);
}

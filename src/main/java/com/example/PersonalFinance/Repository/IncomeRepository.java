package com.example.PersonalFinance.Repository;

import com.example.PersonalFinance.Entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {
    List<Income> findAllByUserId(UUID userId);
}


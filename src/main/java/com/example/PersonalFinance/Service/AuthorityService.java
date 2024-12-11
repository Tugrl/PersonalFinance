package com.example.PersonalFinance.Service;

import com.example.PersonalFinance.Entity.Authority;
import com.example.PersonalFinance.Repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public Optional<Authority> findByName(String name) {
        return authorityRepository.findByName(name);
    }

    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    public Optional<Authority> findById(UUID id) {
        return authorityRepository.findById(id);
    }

    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }
}

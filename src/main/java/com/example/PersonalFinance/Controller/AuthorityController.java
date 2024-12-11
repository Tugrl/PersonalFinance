package com.example.PersonalFinance.Controller;

import com.example.PersonalFinance.Dto.AuthorityDTO;
import com.example.PersonalFinance.Entity.Authority;
import com.example.PersonalFinance.Service.AuthorityService;
import com.example.PersonalFinance.Mapper.AuthorityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authorities")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private AuthorityMapper authorityMapper;

    @GetMapping
    public List<AuthorityDTO> getAllAuthorities() {
        return authorityService.findAll().stream()
                .map(authorityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorityDTO> getAuthorityById(@PathVariable UUID id) {
        Optional<AuthorityDTO> authorityDTO = authorityService.findById(id)
                .map(authorityMapper::toDTO);
        return authorityDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AuthorityDTO createAuthority(@RequestBody AuthorityDTO authorityDTO) {
        Authority authority = authorityMapper.toEntity(authorityDTO);
        return authorityMapper.toDTO(authorityService.save(authority));
    }
}

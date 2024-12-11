package com.example.PersonalFinance.Mapper;

import com.example.PersonalFinance.Dto.AuthorityDTO;
import com.example.PersonalFinance.Entity.Authority;
import org.springframework.stereotype.Component;

@Component
public class AuthorityMapper {

    public AuthorityDTO toDTO(Authority authority) {
        AuthorityDTO dto = new AuthorityDTO();
        dto.setId(authority.getId());
        dto.setName(authority.getName());
        return dto;
    }

    public Authority toEntity(AuthorityDTO dto) {
        Authority authority = new Authority();
        authority.setId(dto.getId());
        authority.setName(dto.getName());
        return authority;
    }
}

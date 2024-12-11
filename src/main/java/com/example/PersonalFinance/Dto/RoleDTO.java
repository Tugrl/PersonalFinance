package com.example.PersonalFinance.Dto;

import java.util.Set;
import java.util.UUID;

public class RoleDTO {
    private UUID id;
    private String name;
    private Set<AuthorityDTO> authorities;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AuthorityDTO> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityDTO> authorities) {
        this.authorities = authorities;
    }
}

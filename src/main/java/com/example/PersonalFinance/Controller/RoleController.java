package com.example.PersonalFinance.Controller;

import com.example.PersonalFinance.Dto.RoleDTO;
import com.example.PersonalFinance.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping
    public ResponseEntity<RoleDTO> addRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO savedRole = roleService.save(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @PostMapping("/{id}/authorities")
    public ResponseEntity<RoleDTO> addAuthorityToRole(@PathVariable UUID id, @RequestBody Map<String, String> request) {

        RoleDTO updatedRole = roleService.addAuthorityToRole(id, request.get("authorityName"));
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}/authorities/{authorityName}")
    public ResponseEntity<RoleDTO> deleteAuthorityFromRole(@PathVariable UUID id, @PathVariable String authorityName) {

        RoleDTO updatedRole = roleService.removeAuthorityFromRole(id, authorityName);
        return ResponseEntity.ok(updatedRole);
    }
}

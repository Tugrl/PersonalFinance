package com.example.PersonalFinance.Controller;

import com.example.PersonalFinance.Dto.PasswordDTO;
import com.example.PersonalFinance.Dto.UserDTO;
import com.example.PersonalFinance.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        Optional<UserDTO> userDTO = userService.getUserById(id);
        return userDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.saveUser(userDTO);
        return ResponseEntity.status(201).body(createdUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDTO> addRoleToUser(@PathVariable UUID id, @RequestBody Map<String, String> request) {
        String roleName = request.get("roleName");
        UserDTO updatedUser = userService.addRoleToUser(id, roleName);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        UserDTO user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }
    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordDTO passwordDTO) {
        if (userService.updatePassword(passwordDTO.getOldPassword(), passwordDTO.getNewPassword())) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(400).body("Failed to update password. Old password might be incorrect.");
        }
    }
}

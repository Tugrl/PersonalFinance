package com.example.PersonalFinance.Logic;
import com.example.PersonalFinance.Entity.Role;
import com.example.PersonalFinance.Entity.User;
import com.example.PersonalFinance.Repository.RoleRepository;
import com.example.PersonalFinance.Repository.UserRepository;

import com.example.PersonalFinance.Security.EncoderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class UserLogic {
    @Autowired
    private EncoderConfig encoderConfig;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new NoSuchElementException("ROLE_USER not found"));
        user.getRoles().add(userRole);
        user.setPassword(encoderConfig.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
    public User saveAdminUser(User user) {
        Role userRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new NoSuchElementException("ROLE_USER not found"));
        user.getRoles().add(userRole);
        user.setPassword(encoderConfig.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}

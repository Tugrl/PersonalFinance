package com.example.PersonalFinance.Service;

import com.example.PersonalFinance.Dto.UserDTO;
import com.example.PersonalFinance.Entity.Role;
import com.example.PersonalFinance.Entity.User;
import com.example.PersonalFinance.Logic.RoleLogic;
import com.example.PersonalFinance.Logic.UserLogic;
import com.example.PersonalFinance.Mapper.UserMapper;
import com.example.PersonalFinance.Repository.RoleRepository;
import com.example.PersonalFinance.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserLogic userLogic;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleLogic roleLogic;
    @Autowired
    private RoleRepository roleRepository;


    @PreAuthorize("hasAuthority('ACCESS_PRIVILEGE')")
    public List<UserDTO> getAllUsers() {
        return userLogic.getAllUsers().stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(UUID id) {
        return userLogic.getUserById(id)
                .map(userMapper::userToUserDTO);
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        User savedUser = userLogic.saveUser(user);
        return userMapper.userToUserDTO(savedUser);
    }

    public UserDTO saveAdminUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        User savedUser = userLogic.saveAdminUser(user);
        return userMapper.userToUserDTO(savedUser);
    }

    @PreAuthorize("hasAnyAuthority('DELETE_PRIVILEGE')")
    public void deleteUser(UUID id) {
        userLogic.deleteUser(id);
    }
    public Optional<User> findById(UUID id) {
        return userLogic.getUserById(id);
    }

    public UserDTO addRoleToUser(UUID userId, String roleName) {
        User user = userLogic.getUserById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Role role = roleLogic.getRoleByName(roleName)
                .orElseThrow(() -> new NoSuchElementException("Role not found"));
        user.getRoles().add(role);

        return userMapper.userToUserDTO(userLogic.saveUser(user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) userLogic.loadUserByUsername(username);
        return user;
    }

    public UserDTO findByUsername(String username) {
        User user = (User) userLogic.loadUserByUsername(username);
        return userMapper.userToUserDTO(user);
    }

    public boolean updatePassword(String oldPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

}

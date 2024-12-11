package com.example.PersonalFinance.Service;

import com.example.PersonalFinance.Dto.UserDTO;
import com.example.PersonalFinance.Entity.User;
import com.example.PersonalFinance.Logic.UserLogic;
import com.example.PersonalFinance.Mapper.UserMapper;
import com.example.PersonalFinance.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    public List<UserDTO> getAllUsers() {
        return userLogic.getAllUsers().stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public Optional<UserDTO> getUserById(UUID id) {
        return userLogic.getUserById(id)
                .map(userMapper::userToUserDTO);
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        User savedUser = userLogic.saveUser(user);
        return userMapper.userToUserDTO(savedUser);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteUser(UUID id) {
        userLogic.deleteUser(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public Optional<User> findById(UUID id) {
        return userLogic.getUserById(id);
    }

   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserDTO addRoleToUser(UUID userId, String roleName) {
        User user = userLogic.getUserById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
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

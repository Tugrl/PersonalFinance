package com.example.PersonalFinance.Mapper;
import com.example.PersonalFinance.Entity.User;
import org.springframework.stereotype.Component;
import com.example.PersonalFinance.Dto.UserDTO;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public UserDTO userToUserDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());

        userDTO.setSurname(user.getSurname());
        userDTO.setIdentityNumber(user.getIdentityNumber());
        userDTO.setBirthDate(user.getBirthDate());

        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles().stream().map(roleMapper::toDTO).collect(Collectors.toSet()));
        return userDTO;
    }
    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setIdentityNumber(userDTO.getIdentityNumber());
        user.setBirthDate(userDTO.getBirthDate());

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRoles(userDTO.getRoles().stream().map(roleMapper::toEntity).collect(Collectors.toSet()));
        return user;
    }

}


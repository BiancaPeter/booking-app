package com.spring.booking.service;

import com.spring.booking.DTO.RegisterDTO;
import com.spring.booking.model.Role;
import com.spring.booking.model.RoleType;
import com.spring.booking.model.User;
import com.spring.booking.repository.RoleRepository;
import com.spring.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private RoleService roleService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,RoleService roleService,PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.roleService=roleService;
        this.passwordEncoder=passwordEncoder;
    }


    public User register(RegisterDTO newUser) {
        Optional<User> foundUserOptional = userRepository.findUserByUsername(newUser.getUsername());
        if (foundUserOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CREATED, "already exist");
        }
        User user = new User();
        user.setUsername(newUser.getUsername());

        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Role foundRole = roleRepository.findByRoleType(newUser.getRoleType());
        if (foundRole != null) {
            setRoleOfUser(user, foundRole);
        } else if (newUser.getRoleType().equals(RoleType.ROLE_ADMIN) || newUser.getRoleType().equals(RoleType.ROLE_CLIENT)) {
            Role newRole = roleService.addRole(newUser.getRoleType());
            setRoleOfUser(user, newRole);
        }
        return userRepository.save(user);

    }
    private static void setRoleOfUser(User user, Role role) {
        user.getRoleList().add(role);
        role.getUserList().add(user);
    }
}


package com.spring.booking.service;

import com.spring.booking.model.Role;
import com.spring.booking.model.RoleType;
import com.spring.booking.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role addRole(RoleType roleType) {
        Role newRole = new Role();
        newRole.setRoleType(roleType);
        newRole.setUserList(new ArrayList<>());
        return roleRepository.save(newRole);
    }
}

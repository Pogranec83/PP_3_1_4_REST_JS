package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RolesRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RolesRepository rolesRepository;

    public RoleServiceImpl( RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Set<Role> findRoles(List<Long> longs) {

        return new HashSet<>(rolesRepository.findAllById(longs));
    }

    @Override
    public List<Role> getAllRole() {
        return rolesRepository.findAll();
    }
}

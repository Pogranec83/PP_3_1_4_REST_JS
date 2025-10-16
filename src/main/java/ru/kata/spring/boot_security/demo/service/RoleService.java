package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.RoleDto;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Set<RoleDto> findRoles(List<Long> roles);
    List<RoleDto> getAllRole();
}

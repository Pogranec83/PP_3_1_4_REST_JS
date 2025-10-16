package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.RoleDto;
import ru.kata.spring.boot_security.demo.model.UserDto;

import java.util.List;
import java.util.Set;

public interface UsersService {

    UserDto getUserEmail(String email);

    List<UserDto> getAllUsers();

    UserDto getUserById(long id);

    void saveNewUser(UserDto user, Set<RoleDto> roles);

    UserDto updateUser(UserDto userUpdate, Set<RoleDto> roles);

    void deleteUserById(long id);
}

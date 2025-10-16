package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UsersService {

    User getUserEmail(String email);

    List<User> getAllUsers();

    User getUserById(long id);

    void saveNewUser(User user, Set<Role> roles);

    User updateUser(User userUpdate, Set<Role> roles);

    void deleteUserById(long id);
}

package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;

import java.util.List;
import java.util.Set;

@Service
public class UsersServiceImpl implements UsersService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsersRepository usersRepository;

    public UsersServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UsersRepository usersRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usersRepository = usersRepository;
    }

    @Override
    public User getUserEmail(String email) {
        return usersRepository.getUsersByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }


    @Override
    public User getUserById(long id) {
        return usersRepository.getById(id);
    }

    @Override
    @Transactional
    public void saveNewUser(User user, Set<Role> roles) {
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User userUpdate, Set<Role> roles) {

        String password = getUserById(userUpdate.getId()).getPassword();

        if (userUpdate.getPassword().isEmpty()) {
            userUpdate.setPassword(password);
        } else {
            userUpdate.setPassword(bCryptPasswordEncoder.encode(userUpdate.getPassword()));
        }
        userUpdate.setRoles(roles);
        return usersRepository.save(userUpdate);
    }

    @Override
    @Transactional
    public void deleteUserById(long id) {
        usersRepository.deleteById(id);
    }

}

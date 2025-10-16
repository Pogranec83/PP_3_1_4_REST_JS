package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersService;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestControllerUser {
    private final UsersService usersService;

    @Autowired
    public RestControllerUser(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> listUsers() {
        final List<User> clients = usersService.getAllUsers();

        return clients != null &&  !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

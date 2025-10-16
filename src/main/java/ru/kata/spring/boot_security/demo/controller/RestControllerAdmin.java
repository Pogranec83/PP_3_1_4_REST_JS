package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UsersService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestControllerAdmin {
    private final UsersService usersService;

    @Autowired
    public RestControllerAdmin(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> listUsers() {
        final List<User> clients = usersService.getAllUsers();

        return clients != null &&  !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin/email")
    public ResponseEntity<User> getUserEmail(Principal principal){
        User user = usersService.getUserEmail(principal.getName());
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") long id) {
        final User user = usersService.getUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin")
    public ResponseEntity<?> newUser(@RequestBody User user) {
        usersService.saveNewUser(user, user.getRoles());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/admin")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        final User person = usersService.updateUser(user, user.getRoles());

        return person != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") long id) {
      usersService.deleteUserById(id);

      return new ResponseEntity<>(HttpStatus.OK);
    }
}

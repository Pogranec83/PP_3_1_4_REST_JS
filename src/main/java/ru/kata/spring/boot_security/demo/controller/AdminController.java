package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.UserDto;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UsersService;

import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsersService usersService;
    private final RoleService roleService;

    public AdminController(UsersService usersService, RoleService roleService) {
        this.usersService = usersService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAllUsers( Model model,
                               Principal principal,
                               @AuthenticationPrincipal UserDto user) {

        model.addAttribute("roleList", roleService.getAllRole());
        model.addAttribute("users", usersService.getAllUsers());
        model.addAttribute("userByEmail", user);
        return "index";
    }

    @PostMapping
    public String create(@ModelAttribute("user") UserDto user,
                         @RequestParam("listRoles") ArrayList<Long> roles) {

        String email = user.getEmail();
        for(UserDto person : usersService.getAllUsers()){
            if(person.getEmail().equals(email)){
                return "isUser";
            }
        }

        usersService.saveNewUser(user, roleService.findRoles(roles));
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") UserDto user,
                         @RequestParam("listRoles") ArrayList<Long> roles) {

        usersService.updateUser(user, roleService.findRoles(roles));
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        usersService.deleteUserById(id);
        return "redirect:/admin";
    }
}
package ru.kata.spring.boot_security.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "users")
public class UserDto implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "не может быть пустым")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "не может быть пустым")
    @Size(min = 2, max = 55, message = "не может быть меньше 2 или больше 55 букв")
    @Column (name = "surname")
    private String surname;

    @Column(name = "age")
    private int age;

    @NotEmpty
    @Email
    @Column(unique = true, name = "email")
    private String email;

    @NotEmpty(message = "не может быть пустым")
    @Column(name = "password")
    private String password;

    public UserDto() {
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleDto> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

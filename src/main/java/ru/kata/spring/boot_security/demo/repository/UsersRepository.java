package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.UserDto;

@Repository
public interface UsersRepository extends JpaRepository<UserDto, Long> {

    @Query("select u from UserDto u where u.email = :email")
    UserDto getUsersByEmail(@Param("email") String email);
}

package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = :email")
    User getUsersByEmail(@Param("email") String email);
}

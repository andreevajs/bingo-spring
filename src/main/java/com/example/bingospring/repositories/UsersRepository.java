package com.example.bingospring.repositories;

import com.example.bingospring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<User, Long> {
    @Query("SELECT u from User u WHERE u.username = :username")
    User findByUsername(String username);

    @Query("SELECT u from User u where UPPER(u.email) = UPPER(:email)")
    User findByEmail(String email);
}

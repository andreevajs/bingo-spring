package com.example.bingospring.repositories;

import com.example.bingospring.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RolesRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r from Role r WHERE UPPER(r.name) = UPPER(:name)")
    Role findByName(String name);
    @Query("SELECT r from Role r WHERE UPPER(r.name) = 'USER'")
    Role getDefault();
}

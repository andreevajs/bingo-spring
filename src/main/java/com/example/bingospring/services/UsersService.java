package com.example.bingospring.services;

import com.example.bingospring.entities.Role;
import com.example.bingospring.entities.User;
import com.example.bingospring.repositories.RolesRepository;
import com.example.bingospring.repositories.UsersRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RolesRepository rolesRepository;

    BCryptPasswordEncoder encoder;

    public UsersService() {
        encoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findById(Long userId) {
        Optional<User> user = usersRepository.findById(userId);
        return user.orElse(new User());
    }

    public List<User> getAll() {
        return usersRepository.findAll();
    }

    public boolean usernameAvailable(String username) {
        return usersRepository.findByUsername(username) == null;
    }

    public boolean emailAvailable(String email) {
        return usersRepository.findByEmail(email) == null;
    }

    public User create(User user) throws SQLException {

        Role role = rolesRepository.getDefault();

        if (role == null) {
            throw new SQLException("no default role \"User\" in database");
        }

        user.setRoles(Collections.singleton(role));
        user.setPasswordHash(encoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public boolean SetRole(User user, String name) {
        Role role = rolesRepository.findByName(name);
        if (role == null)
            return false;
        user.setRoles(Collections.singleton(role));
        usersRepository.save(user);
        return true;
    }

    public boolean delete(Long userId) {
        if (usersRepository.findById(userId).isPresent()) {
            usersRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
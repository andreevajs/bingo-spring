package com.example.bingospring.services;

import com.example.bingospring.entities.Role;
import com.example.bingospring.entities.User;
import com.example.bingospring.repositories.RolesRepository;
import com.example.bingospring.repositories.UsersRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UsersServiceTests {

    @Autowired
    UsersService usersService;

    @MockBean
    UsersRepository usersRepository;
    @MockBean
    RolesRepository rolesRepository;


    @Test
    void loadUserByUsername() {
    }

    @Test
    void findById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void usernameAvailable_DoesntUpdateUser() {
        usersService.usernameAvailable("username_test");
        Mockito.verify(usersRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void usernameAvailable_Calls_FindByUsername() {
        usersService.usernameAvailable("username_test");
        Mockito.verify(usersRepository, Mockito.times(1)).findByUsername("username_test");
    }

    @Test
    void usernameAvailable_Available_ReturnsTrue() {
        boolean available = usersService.usernameAvailable("username_test");
        Assert.assertTrue(available);
    }

    @Test
    void usernameAvailable_NotAvailable_ReturnsFalse() {
        Mockito.doReturn(new User())
                .when(usersRepository)
                .findByUsername("username_test");

        boolean available = usersService.usernameAvailable("username_test");
        Assert.assertFalse(available);
    }

    @Test
    void emailAvailable_DoesntUpdateUser() {
        usersService.emailAvailable("email@test");
        Mockito.verify(usersRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    void emailAvailable_Calls_FindByEmail() {
        usersService.emailAvailable("email@test");
        Mockito.verify(usersRepository, Mockito.times(1)).findByEmail("email@test");
    }

    @Test
    void emailAvailable_Available_ReturnsTrue() {
        boolean available = usersService.emailAvailable("email@test");
        Assert.assertTrue(available);
    }

    @Test
    void emailAvailable_NotAvailable_ReturnsFalse() {
        Mockito.doReturn(new User())
                .when(usersRepository)
                .findByEmail("email@test");

        boolean available = usersService.emailAvailable("email@test");
        Assert.assertFalse(available);
    }

    @Test
    void create_ThrowsException_IfNoUserRoleInDatabase() {
        User user = new User("John","john@gmail.com","Don'tTellMeWhatICan'tDo");

        Exception exception = assertThrows(SQLException.class, () -> {
            usersService.create(user);
        });
    }

    @Test
    void create_SavesToRepository() throws SQLException {
        Mockito.doReturn(new Role())
                .when(rolesRepository)
                .getDefault();

        User user = new User("John","john@gmail.com","Don'tTellMeWhatICan'tDo");
        usersService.create(user);
        Mockito.verify(usersRepository, Mockito.times(1)).save(user);
    }

    @Test
    void create_SetsDefaultUserRole() throws SQLException {
        Role defaultRole = new Role("default_test");
        Mockito.doReturn(defaultRole)
                .when(rolesRepository)
                .getDefault();

        User user = new User("John","john@gmail.com","Don'tTellMeWhatICan'tDo");
        usersService.create(user);
        Assert.assertTrue("expected new user to have a default role", user.getRoles().contains(defaultRole));
    }

    @Test
    void create_DoesntSaveRawPassword() throws SQLException {
        Mockito.doReturn(new Role())
                .when(rolesRepository)
                .getDefault();

        User user = new User("John","john@gmail.com","raw_password");
        usersService.create(user);
        Assert.assertNotEquals("raw_password", user.getPasswordHash());
    }

    @Test
    void delete() {
    }
}
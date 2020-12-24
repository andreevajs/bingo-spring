package com.example.bingospring.controllers;

import com.example.bingospring.entities.User;
import com.example.bingospring.models.RegisterModel;
import com.example.bingospring.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping(path = "/register")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
public class RegistrationController {

    @Autowired
    private UsersService usersService;

    @GetMapping()
    public String registration(RegisterModel model) {
        logger.info("GET");
        return "register";
    }

    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody RegisterModel model) throws SQLException {
        logger.info("POST");
        if(model.username == null || model.username.length() == 0) {
            return new ResponseEntity<>("имя пользователя не задано", HttpStatus.BAD_REQUEST);
        } else if(model.email == null || model.email.length() == 0) {
            return new ResponseEntity<>("email не задан", HttpStatus.BAD_REQUEST);
        } else if (!model.password.equals(model.passwordConfirm)){
            return new ResponseEntity<>("пароли не совпадают", HttpStatus.BAD_REQUEST);
        } else if (!usersService.usernameAvailable(model.username)) {
            return new ResponseEntity<>("пользователь с таким именем уже существует", HttpStatus.BAD_REQUEST);
        } else if (!usersService.emailAvailable(model.email)) {
            return new ResponseEntity<>("email "+ model.email + " занят", HttpStatus.BAD_REQUEST);
        }

        User user = usersService.create(new User(model.username, model.email, model.password));

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

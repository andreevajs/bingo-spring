package com.example.bingospring.controllers;

import com.example.bingospring.entities.BingoCell;
import com.example.bingospring.entities.User;
import com.example.bingospring.models.BingoCellModel;
import com.example.bingospring.services.BingoService;
import com.example.bingospring.utils.ModelEntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/bingocell")
public class BingoCellsController {

    @Autowired
    private BingoService bingoService;

    @Autowired
    private ModelEntityConverter converter;

    Logger logger = LoggerFactory.getLogger(BingoCellsController.class);

    @GetMapping
    public ResponseEntity<?> get(@RequestParam Long id) {
        logger.info("GET");
        if (id == null) {
            return new ResponseEntity<>("клетка не найдена", HttpStatus.BAD_REQUEST);
        }

        BingoCell cell = bingoService.getCell(id);
        if (cell == null) {
            return new ResponseEntity<>("клетка не найдена", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(converter.toModel(cell), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> create(Authentication auth, @RequestParam Long bingo, @RequestBody BingoCellModel model) {
        logger.info("POST");
        if (auth == null || !auth.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (model == null || bingo == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (model.id == null) {
            return createCell(auth, bingo, model);
        } else {
            return updateCell(auth, bingo, model);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(Authentication auth, @RequestParam Long id) {
        logger.info("DELETE");
        if (auth == null || !auth.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (id == null) {
            return new ResponseEntity<>("клетка не найдена", HttpStatus.BAD_REQUEST);
        } else {
            boolean deleted = bingoService.deleteCell(id, (User)auth.getPrincipal());
            if (deleted)
                return new ResponseEntity<>("bingo", HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    private ResponseEntity<?> createCell(Authentication auth, Long bingo, BingoCellModel model) {
        BingoCell cell = bingoService.addCell(bingo, model.row, model.column, model.content,(User)auth.getPrincipal());
        if (cell == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(converter.toModel(cell),HttpStatus.OK);
        }
    }

    private ResponseEntity<?> updateCell(Authentication auth, Long bingo, BingoCellModel model) {
        boolean updated = bingoService.updateCell(model.id, model.content, (User)auth.getPrincipal());
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else  {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
package com.example.bingospring.controllers;

import com.example.bingospring.entities.Bingo;
import com.example.bingospring.entities.BingoCell;
import com.example.bingospring.entities.User;
import com.example.bingospring.models.BingoCellModel;
import com.example.bingospring.models.BingoModel;
import com.example.bingospring.models.SimpleBingoModel;
import com.example.bingospring.services.BingoService;
import com.example.bingospring.utils.ModelEntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/bingo")
public class BingoController {

    @Autowired
    private BingoService bingoService;

    @Autowired
    private ModelEntityConverter converter;

    Logger logger = LoggerFactory.getLogger(BingoController.class);

    @GetMapping()
    public ResponseEntity<?> get() {
        logger.info("GET");
        List<SimpleBingoModel> bingoModels = new ArrayList<>();
        List<Bingo> bingoList = bingoService.getAllBingo();
        if (bingoList != null) {
            for (Bingo bingo: bingoList) {
                bingoModels.add(new SimpleBingoModel(bingo.getId(), bingo.getName()));
            }
        }
        return new ResponseEntity<>(bingoModels, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> get(Authentication auth, @PathVariable Long id) {
        logger.info("GET id");
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Bingo bingo = bingoService.getBingo(id);
        if (bingo == null) {
            return new ResponseEntity<>("бинго не найдено", HttpStatus.BAD_REQUEST);
        } else {
            BingoModel model = converter.toModel(bingo);
            if (auth != null) {
                User user = (User)auth.getPrincipal();
                if (bingo.getCreator().getId() == user.getId()) {
                    model.canEdit = true;
                }
            }
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(Authentication auth, @RequestBody BingoModel model) {
        logger.info("POST");
        if (auth == null || !auth.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (model == null) {
            return new ResponseEntity<>("пустое бинго", HttpStatus.BAD_REQUEST);
        } else if (model.id == null) {
            return createBingo(auth, model);
        } else {
            return updateBingo(auth, model);
        }
    }

    @PutMapping()
    public ResponseEntity<?> addCells(Authentication auth, @RequestBody List<BingoCellModel> model, @RequestParam Long id) {
        logger.info("PUT");
        if (auth == null || !auth.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else if (model == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            User user = (User)auth.getPrincipal();
            List<BingoCell> cells = new ArrayList<>();
            for (BingoCellModel cell: model) {
                cells.add(converter.toEntity(cell));
            }
            List<BingoCell> addedCells = bingoService.addCells(id, cells, user);
            if (addedCells == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                model.clear();
                for (BingoCell cell: addedCells) {
                    model.add(converter.toModel(cell));
                }
                return new ResponseEntity<>(model,HttpStatus.CREATED);
            }

        }
    }

    private ResponseEntity<?> createBingo(Authentication auth, BingoModel model){
        User user = (User)auth.getPrincipal();
        Bingo bingo = bingoService.createBingo(model.name, user);
        if (bingo == null) {
            return new ResponseEntity<>("не удалось создать бинго", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(bingo.getId(), HttpStatus.CREATED);
        }
    }

    private ResponseEntity<?> updateBingo(Authentication auth, BingoModel model){
        User user = (User)auth.getPrincipal();
        boolean updated = bingoService.updateBingo(model.id, model.name, model.backgroundColor, user);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

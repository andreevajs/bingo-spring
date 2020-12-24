package com.example.bingospring.services;

import com.example.bingospring.entities.Bingo;
import com.example.bingospring.entities.BingoCell;
import com.example.bingospring.entities.User;
import com.example.bingospring.repositories.BingoCellRepository;
import com.example.bingospring.repositories.BingoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BingoService {
    @Autowired
    BingoRepository bingoRepository;

    @Autowired
    BingoCellRepository bingoCellRepository;

    public Bingo getBingo(Long id) {
        Optional<Bingo> bingo = bingoRepository.findById(id);
        return bingo.orElse(null);
    }

    public List<Bingo> getAllBingo() {
        return bingoRepository.findAll();
    }

    public Bingo createBingo(String name, User user) {
        if (name == null || name.equals("")){
            return null;
        } else {
            return bingoRepository.save(new Bingo(name, user));
        }
    }

    public boolean updateBingo(Long id, String name, String bgColor, User user) {
        Bingo bingo = getBingo(id);
        if (bingo == null
            || !bingo.getCreator().getId().equals(user.getId())
            || name == null || name.equals("")
            || !(bgColor== null || bgColor.length() == 8 || bgColor.length() == 6)){
            return false;
        } else {
            bingo.setName(name);
            bingo.setBackgroundColor(bgColor);
            bingoRepository.save(bingo);
            return true;
        }
    }

    public boolean deleteBingo(Long id, User user) {
        Bingo bingo = getBingo(id);
        if (bingo == null || !bingo.getCreator().getId().equals(user.getId())){
            return false;
        } else {
            for (BingoCell cell: bingo.getCells()) {
                bingoCellRepository.delete(cell);
            }
            bingoRepository.delete(bingo);
            return true;
        }
    }

    public BingoCell getCell(Long cellId) {
        Optional<BingoCell> cell = bingoCellRepository.findById(cellId);
        return (cell.orElse(null));
    }

    public BingoCell addCell(Long bingoId, Integer row, Integer column, String content, User user) {
        Bingo bingo = getBingo(bingoId);
        if (bingo == null
            || !bingo.getCreator().getId().equals(user.getId())
            || content == null || content.equals("")
            || row < 1 || row > 10 || column < 1 || column > 10){
            return null;
        } else if (bingoCellRepository.findAtPosition(bingo, row, column) != null) {
            return null;
        } else {
            return bingoCellRepository.save(new BingoCell(bingo, row, column, content));
        }
    }

    public List<BingoCell> addCells(Long bingoId, List<BingoCell> cells, User user) {
        Bingo bingo = getBingo(bingoId);
        if (bingo == null
                || !bingo.getCreator().getId().equals(user.getId())){
            return null;
        } else {
            for (BingoCell cell: cells) {
                if (cell.getContent() == null || cell.getContent().equals("")
                    || cell.getRow() < 1 || cell.getRow() > 10 || cell.getColumn() < 1 || cell.getColumn() > 10) {
                    return null;
                }
                for (BingoCell dbCell: bingo.getCells()) {
                    if (cell.getRow().equals(dbCell.getRow()) && cell.getColumn().equals(dbCell.getColumn()))
                        return null;
                }
            }
            for (BingoCell cell: cells) {
                cell.setBingo(bingo);
                cell.setId(bingoCellRepository.save(cell).getId());
            }
            return cells;
        }
    }

    public boolean updateCell(Long cellId, String content, User user) {
        BingoCell cell = getCell(cellId);
        if (cell == null
            || !cell.getBingo().getCreator().getId().equals(user.getId())
            || content == null || content.equals("")) {
            return false;
        } else {
            cell.setContent(content);
            bingoCellRepository.save(cell);
            return true;
        }
    }

    public boolean deleteCell(Long cellId, User user) {
        BingoCell cell = getCell(cellId);
        if (cell == null
            || !cell.getBingo().getCreator().getId().equals(user.getId())){
            return false;
        } else {
            bingoCellRepository.delete(cell);
            return true;
        }
    }
}

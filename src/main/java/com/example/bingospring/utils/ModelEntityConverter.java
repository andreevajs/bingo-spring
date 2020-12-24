package com.example.bingospring.utils;

import com.example.bingospring.entities.Bingo;
import com.example.bingospring.entities.BingoCell;
import com.example.bingospring.models.BingoCellModel;
import com.example.bingospring.models.BingoModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ModelEntityConverter {
    public BingoModel toModel(Bingo bingo) {
        BingoModel model = new BingoModel();
        model.name = bingo.getName();
        model.id = bingo.getId();
        model.creator = bingo.getCreator().getUsername();
        model.backgroundColor = bingo.getBackgroundColor();

        model.cells = new ArrayList<>();
        for (BingoCell cell: bingo.getCells()) {
            model.cells.add(toModel(cell));
        }
        return model;
    }

    public BingoCellModel toModel(BingoCell cell) {
        BingoCellModel model = new BingoCellModel();
        model.id = cell.getId();
        model.row = cell.getRow();
        model.column = cell.getColumn();
        model.content = cell.getContent();
        return model;
    }

    public BingoCell toEntity(BingoCellModel model) {
        BingoCell cell = new BingoCell();
        cell.setId(model.id);
        cell.setRow(model.row);
        cell.setColumn(model.column);
        cell.setContent(model.content);
        return cell;
    }
}

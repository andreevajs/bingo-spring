package com.example.bingospring.repositories;

import com.example.bingospring.entities.Bingo;
import com.example.bingospring.entities.BingoCell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BingoCellRepository extends JpaRepository<BingoCell, Long> {
    @Query("DELETE FROM BingoCell WHERE id = :cellId")
    void deleteById(Long cellId);
    @Query("select c FROM BingoCell c WHERE c.bingo = :bingo AND c.row = :row AND c.column = :column")
    BingoCell findAtPosition(Bingo bingo, Integer row, Integer column);
}

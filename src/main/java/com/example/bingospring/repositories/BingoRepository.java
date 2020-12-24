package com.example.bingospring.repositories;

import com.example.bingospring.entities.Bingo;
import com.example.bingospring.entities.BingoCell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BingoRepository extends JpaRepository<Bingo, Long> {
    @Query("SELECT b from Bingo b WHERE UPPER(b.name) LIKE UPPER('%:nameSubstring%')")
    List<Bingo> findByNameSubstring(@Param("nameSubstring") String nameSubstring);
}

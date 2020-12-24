package com.example.bingospring.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bingo_cells")
public class BingoCell {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Bingo bingo;
    @Column(name = "content")
    private String content;
    @Column(name = "row")
    private Integer row;
    @Column(name = "col")
    private Integer column;

    public BingoCell(Bingo bingo, Integer row, Integer column, String content) {
        this.bingo = bingo;
        this.content = content;
        this.row = row;
        this.column = column;
    }

    public BingoCell() {

    }

    public Long getId() {
        return id;
    }

    public Bingo getBingo() {
        return bingo;
    }

    public void setBingo(Bingo bingo) {
        this.bingo = bingo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}

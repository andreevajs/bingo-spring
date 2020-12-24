package com.example.bingospring.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bingo")
public class Bingo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "bingo")
    private Set<BingoCell> cells;
    @ManyToOne
    private User creator;
    @Column(name = "bg_color")
    private String backgroundColor;

    public Bingo() {

    }

    public Bingo(String name, User creator) {
        this.name = name;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BingoCell> getCells() {
        return cells;
    }

    public void setCells(Set<BingoCell> cells) {
        this.cells = cells;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}

package com.example.BoardGameEventBackend.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "boardgamecategory")
@EntityListeners(AuditingEntityListener.class)
@ToString
public class BoardGameCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull
    private String name;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "boardGameCategory")
    private List<BoardGame> boardGames = new ArrayList<>();

    public void addBoardGame(BoardGame boardGame){
        boardGames.add(boardGame);
    }

    public void removeBoardGame(BoardGame boardGame){
        boardGames.remove(boardGame);
    }
}

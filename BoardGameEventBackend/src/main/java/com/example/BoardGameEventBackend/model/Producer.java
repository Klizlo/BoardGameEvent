package com.example.BoardGameEventBackend.model;

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
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "producer")
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Producer {

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

    @OneToMany(mappedBy = "producer")
    @ToString.Exclude
    List<BoardGame> boardGames = new ArrayList<>();

    public void addBoardGame(BoardGame boardGame){
        boardGames.add(boardGame);
    }

    public void removeBoardGame(BoardGame boardGame){
        boardGames.remove(boardGame);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return id.equals(producer.id) && name.equals(producer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

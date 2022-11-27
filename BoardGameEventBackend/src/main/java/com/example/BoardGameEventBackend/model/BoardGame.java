package com.example.BoardGameEventBackend.model;

import com.example.BoardGameEventBackend.converter.AgeRestrictionConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "board_game")
@EntityListeners(AuditingEntityListener.class)
@ToString
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @Min(1)
    private int minNumberOfPlayers;

    @Column(nullable = false)
    @Max(6)
    private int maxNumberOfPlayers;

    @Column(nullable = false)
    @Convert(converter = AgeRestrictionConverter.class)
    @NotNull
    private AgeRestriction ageRestriction;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_game_category_id", nullable = false)
    @NotNull
    private BoardGameCategory boardGameCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producer_id", nullable = false)
    @NotNull
    private Producer producer;

    @OneToMany(mappedBy = "boardGame")
    @ToString.Exclude
    private List<Event> events = new ArrayList<>();

    public void addEvent(Event event){
        events.add(event);
    }

    public void removeEvent(Event event){
        events.remove(event);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardGame boardGame = (BoardGame) o;
        return minNumberOfPlayers == boardGame.minNumberOfPlayers && maxNumberOfPlayers == boardGame.maxNumberOfPlayers && id.equals(boardGame.id) && name.equals(boardGame.name) && ageRestriction == boardGame.ageRestriction && boardGameCategory.equals(boardGame.boardGameCategory) && producer.equals(boardGame.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, minNumberOfPlayers, maxNumberOfPlayers, ageRestriction, boardGameCategory, producer);
    }
}

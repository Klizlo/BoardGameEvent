package com.example.BoardGameEventBackend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "event")
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @Min(1)
    private int numberOfPlayers;

    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boardgame_id")
    BoardGame boardGame;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer_id", nullable = false)
    @NotNull
    User organizer;

    @ManyToMany(mappedBy = "events")
    @ToString.Exclude
    Set<User> players = new HashSet<>();

    public void addPlayer(User user){
        players.add(user);
    }

    public void removePlayer(User user){
        players.remove(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return numberOfPlayers == event.numberOfPlayers && id.equals(event.id) && name.equals(event.name) && description.equals(event.description) && boardGame.equals(event.boardGame) && organizer.equals(event.organizer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberOfPlayers, description, boardGame, organizer);
    }
}

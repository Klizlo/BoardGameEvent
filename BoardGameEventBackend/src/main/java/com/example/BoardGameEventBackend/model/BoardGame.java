package com.example.BoardGameEventBackend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "boardgame")
@EntityListeners(AuditingEntityListener.class)
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int minNumberOfPlayers;

    @Column(nullable = false)
    private int maxNumberOfPlayers;

    @Column(nullable = false)
    private age ageRestriction;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boardGameCategoryId")
    private BoardGameCategory boardGameCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producerId")
    private Producer producer;

    public enum age {
        PLUS_SEVEN, PLUS_FOURTEEN, PLUS_EIGHTEEN
    }
}

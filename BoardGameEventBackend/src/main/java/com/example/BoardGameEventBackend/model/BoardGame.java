package com.example.BoardGameEventBackend.model;

import com.example.BoardGameEventBackend.converter.AgeRestrictionConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boardGameCategoryId")
    private BoardGameCategory boardGameCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producerId")
    private Producer producer;
}

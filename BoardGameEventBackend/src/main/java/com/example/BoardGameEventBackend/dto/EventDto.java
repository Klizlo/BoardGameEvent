package com.example.BoardGameEventBackend.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventDto {

    private Long id;
    private String name;
    private int numberOfPlayers;
    private String description;
    private LocalDateTime date;
    private BoardGameDto boardGame;
    private UserDto organizer;

}

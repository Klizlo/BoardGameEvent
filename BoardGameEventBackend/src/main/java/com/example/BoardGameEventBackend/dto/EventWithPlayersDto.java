package com.example.BoardGameEventBackend.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class EventWithPlayersDto {

    private Long id;
    private String name;
    private int numberOfPlayers;
    private String description;
    private LocalDateTime date;
    private BoardGameDto boardGame;
    private UserDto organizer;
    private List<UserDto> players;

}

package com.example.BoardGameEventBackend.dto;

import com.example.BoardGameEventBackend.model.Event;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventWithPlayersDtoMapper {

    public static List<EventWithPlayersDto> mapToEventWithPlayersDtos(List<Event> events){
        return events.stream()
                .map(EventWithPlayersDtoMapper::mapToEventWithPlayersDto)
                .collect(Collectors.toList());
    }

    public static EventWithPlayersDto mapToEventWithPlayersDto(Event event) {
        return EventWithPlayersDto.builder()
                .id(event.getId())
                .name(event.getName())
                .numberOfPlayers(event.getNumberOfPlayers())
                .description(event.getDescription())
                .date(event.getDate())
                .boardGame(BoardGameDtoMapper.mapToBoardGameDto(event.getBoardGame()))
                .organizer(UserDtoMapper.mapToUserDto(event.getOrganizer()))
                .players(UserDtoMapper.mapToUserDtos(event.getPlayers().stream().toList()))
                .build();
    }

}

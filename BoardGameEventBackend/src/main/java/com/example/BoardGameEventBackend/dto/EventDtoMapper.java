package com.example.BoardGameEventBackend.dto;

import com.example.BoardGameEventBackend.model.Event;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventDtoMapper {

    public static List<EventDto> mapToEventDtos(List<Event> events){
        return events.stream()
                .map(EventDtoMapper::mapToEventDto)
                .collect(Collectors.toList());
    }

    public static EventDto mapToEventDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .numberOfPlayers(event.getNumberOfPlayers())
                .description(event.getDescription())
                .date(event.getDate())
                .boardGame(BoardGameDtoMapper.mapToBoardGameDto(event.getBoardGame()))
                .organizer(UserDtoMapper.mapToUserDto(event.getOrganizer()))
                .build();
    }

}

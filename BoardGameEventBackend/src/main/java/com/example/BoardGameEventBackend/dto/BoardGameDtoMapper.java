package com.example.BoardGameEventBackend.dto;

import com.example.BoardGameEventBackend.model.BoardGame;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardGameDtoMapper {

    public static List<BoardGameDto> mapToBoardGameDtos(List<BoardGame> boardGames){
        return boardGames.stream()
                .map(BoardGameDtoMapper::mapToBoardGameDto)
                .collect(Collectors.toList());
    }

    public static BoardGameDto mapToBoardGameDto(BoardGame boardGame) {
        return BoardGameDto.builder()
                .id(boardGame.getId())
                .name(boardGame.getName())
                .minNumberOfPlayers(boardGame.getMinNumberOfPlayers())
                .maxNumberOfPlayers(boardGame.getMaxNumberOfPlayers())
                .ageRestriction(boardGame.getAgeRestriction())
                .producer(ProducerDtoMapper.mapToProducerDto(boardGame.getProducer()))
                .boardGameCategory(BoardGameCategoryDtoMapper
                        .mapToBoardGameCategoryDto(boardGame.getBoardGameCategory()))
                .build();
    }

}

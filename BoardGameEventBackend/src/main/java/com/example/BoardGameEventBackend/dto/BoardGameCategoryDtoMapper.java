package com.example.BoardGameEventBackend.dto;

import com.example.BoardGameEventBackend.model.BoardGameCategory;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class BoardGameCategoryDtoMapper {

    public static List<BoardGameCategoryDto> mapToBoardGameCategoryDtos(List<BoardGameCategory> boardGameCategories){
        return boardGameCategories.stream()
                .map(BoardGameCategoryDtoMapper::mapToBoardGameCategoryDto)
                .collect(Collectors.toList());
    }

    public static BoardGameCategoryDto mapToBoardGameCategoryDto(BoardGameCategory boardGameCategory) {
        return BoardGameCategoryDto.builder()
                .id(boardGameCategory.getId())
                .name(boardGameCategory.getName())
                .build();
    }

}

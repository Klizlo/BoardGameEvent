package com.example.BoardGameEventBackend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardGameCategoryDto {

    private Long id;
    private String name;

}

package com.example.BoardGameEventBackend.dto;

import com.example.BoardGameEventBackend.model.AgeRestriction;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardGameDto {

    private Long id;
    private String name;
    private int minNumberOfPlayers;
    private int maxNumberOfPlayers;
    private AgeRestriction ageRestriction;
    private ProducerDto producer;
    private BoardGameCategoryDto boardGameCategory;

}

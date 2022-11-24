package com.example.BoardGameEventBackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AgeRestriction {

    @JsonProperty("+7")
    PLUS_SEVEN("+7"),
    @JsonProperty("+14")
    PLUS_FOURTEEN("+14"),
    @JsonProperty("+18")
    PLUS_EIGHTEEN("+18");

    private String age;

}

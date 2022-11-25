package com.example.BoardGameEventBackend.dto;

import com.example.BoardGameEventBackend.model.Producer;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class ProducerDtoMapper {

    public static List<ProducerDto> mapToProducerDtos(List<Producer> producers){
        return producers.stream()
                .map(ProducerDtoMapper::mapToProducerDto)
                .collect(Collectors.toList());
    }

    public static ProducerDto mapToProducerDto(Producer producer) {
        return ProducerDto.builder()
                .id(producer.getId())
                .name(producer.getName())
                .build();
    }

}

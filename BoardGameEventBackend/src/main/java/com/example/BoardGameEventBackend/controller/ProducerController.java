package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.dto.ProducerDto;
import com.example.BoardGameEventBackend.dto.ProducerDtoMapper;
import com.example.BoardGameEventBackend.model.Producer;
import com.example.BoardGameEventBackend.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProducerController {
    private final ProducerService producerService;

    @GetMapping("/producers")
    public List<ProducerDto> getAllProducers(){
        return ProducerDtoMapper.mapToProducerDtos(producerService.getAllProducers());
    }

    @GetMapping("/producers/{id}")
    public ProducerDto getProducer(@PathVariable Long id){
        return ProducerDtoMapper.mapToProducerDto(producerService.getProducer(id));
    }

    @PostMapping("/producers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProducerDto saveProducer(@Valid @RequestBody Producer producer){
        return ProducerDtoMapper.mapToProducerDto(producerService.saveProducer(producer));
    }

    @PutMapping("/producers/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProducerDto updateProducer(@PathVariable Long id, @Valid @RequestBody Producer producer){
        return ProducerDtoMapper.mapToProducerDto(producerService.updateProducer(id, producer));
    }

    @DeleteMapping("/producers/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProducer(@PathVariable Long id){
        producerService.delete(id);
    }
}

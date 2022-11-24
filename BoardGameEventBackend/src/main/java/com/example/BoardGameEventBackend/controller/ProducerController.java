package com.example.BoardGameEventBackend.controller;

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
    public List<Producer> getAllProducers(){
        return producerService.getAllProducers();
    }

    @GetMapping("/producers/{id}")
    public Producer getProducer(@PathVariable Long id){
        return producerService.getProducer(id);
    }

    @PostMapping("/producers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Producer saveProducer(@Valid @RequestBody Producer producer){
        return producerService.saveProducer(producer);
    }

    @PutMapping("/producers/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Producer updateProducer(@PathVariable Long id, @Valid @RequestBody Producer producer){
        return producerService.updateProducer(id, producer);
    }

    @DeleteMapping("/producers/{id}")
    public void deleteProducer(@PathVariable Long id){
        producerService.delete(id);
    }
}

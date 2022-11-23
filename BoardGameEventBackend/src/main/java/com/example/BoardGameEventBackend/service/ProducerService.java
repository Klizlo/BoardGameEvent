package com.example.BoardGameEventBackend.service;

import com.example.BoardGameEventBackend.exception.ProducerExistsException;
import com.example.BoardGameEventBackend.exception.ProducerNotFoundExeption;
import com.example.BoardGameEventBackend.model.Producer;
import com.example.BoardGameEventBackend.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerRepository producerRepository;

    public List<Producer> getAllProducers(){
        return producerRepository.findAll();
    }

    public Producer getProducer(Long id){
        return producerRepository.findById(id).orElseThrow(() -> new ProducerNotFoundExeption(id.toString()));
    }

    public Producer saveProducer(Producer producer){
        if(producerRepository.existsByName(producer.getName())){
            throw new ProducerExistsException("Name " + producer.getName() + " is already used.");
        }

        return producerRepository.save(producer);
    }

    public Producer updateProducer(Long id, Producer producer) {

        Producer producerToEdit = producerRepository.findById(id).orElseThrow(() -> new ProducerNotFoundExeption(id.toString()));

        if(producerRepository.existsByName(producer.getName())){
            throw new ProducerExistsException("Name " + producer.getName() + " is already used.");
        }

        producerToEdit.setName(producer.getName());

        return producerRepository.save(producerToEdit);
    }

    public void delete(Long id){
        producerRepository.deleteById(id);
    }
}

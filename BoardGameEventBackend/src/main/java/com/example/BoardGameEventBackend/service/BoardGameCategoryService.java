package com.example.BoardGameEventBackend.service;

import com.example.BoardGameEventBackend.exception.BoardGameCategoryExistsException;
import com.example.BoardGameEventBackend.exception.BoardGameCategoryNotFoundExeption;
import com.example.BoardGameEventBackend.model.BoardGameCategory;
import com.example.BoardGameEventBackend.repository.BoardGameCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardGameCategoryService {

    private final BoardGameCategoryRepository boardGameCategoryRepository;

    public List<BoardGameCategory> getAllBoardGamesCategories(){
        return boardGameCategoryRepository.findAll();
    }

    public BoardGameCategory getBoardGameCategory(Long id){
        return boardGameCategoryRepository.findById(id).orElseThrow(() -> new BoardGameCategoryNotFoundExeption(id.toString()));
    }

    @Transactional
    public BoardGameCategory saveBoardGameCategory(BoardGameCategory boardGameCategory){
        if(boardGameCategoryRepository.existsByName(boardGameCategory.getName())){
            throw new BoardGameCategoryExistsException("Name " + boardGameCategory.getName() + " is already used.");
        }

        return boardGameCategoryRepository.save(boardGameCategory);
    }

    @Transactional
    public BoardGameCategory updateBoardGameCategory(Long id, BoardGameCategory boardGameCategory) {

        BoardGameCategory boardGameCategoryToEdit = boardGameCategoryRepository.findById(id).orElseThrow(() -> new BoardGameCategoryNotFoundExeption(id.toString()));

        if(boardGameCategoryRepository.existsByName(boardGameCategory.getName())){
            throw new BoardGameCategoryExistsException("Name " + boardGameCategory.getName() + " is already used.");
        }

        boardGameCategoryToEdit.setName(boardGameCategory.getName());

        return boardGameCategoryRepository.save(boardGameCategoryToEdit);
    }

    public void delete(Long id){
        boardGameCategoryRepository.deleteById(id);
    }
}

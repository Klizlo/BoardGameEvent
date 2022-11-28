package com.example.BoardGameEventBackend.service;

import com.example.BoardGameEventBackend.exception.BoardGameExistsException;
import com.example.BoardGameEventBackend.exception.BoardGameNotFoundException;
import com.example.BoardGameEventBackend.model.BoardGame;
import com.example.BoardGameEventBackend.repository.BoardGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardGameService{

    private final BoardGameRepository boardGameRepository;

    public List<BoardGame> getAllBoardGames(){
        return boardGameRepository.findAll();
    }

    public BoardGame getBoardGame(Long id){
        return boardGameRepository.findById(id).orElseThrow(() -> new BoardGameNotFoundException(id.toString()));
    }

    @Transactional
    public BoardGame saveBoardGame(BoardGame boardGame){
        if(boardGameRepository.existsByName(boardGame.getName())){
            throw new BoardGameExistsException("Name " + boardGame.getName() + " is already taken");
        }

        return boardGameRepository.save(boardGame);
    }

    @Transactional
    public BoardGame updateBoardGame(Long id, BoardGame boardGame) {
        BoardGame boardGameToEdit = boardGameRepository.findById(id).orElseThrow(() -> new BoardGameNotFoundException(id.toString()));

        if(boardGameRepository.existsByName(boardGame.getName())){
            throw new BoardGameExistsException("Name " + boardGame.getName() + " is already used.");
        }

        boardGameToEdit.setName(boardGame.getName());
        boardGameToEdit.setAgeRestriction(boardGame.getAgeRestriction());
        boardGameToEdit.setMaxNumberOfPlayers(boardGame.getMaxNumberOfPlayers());
        boardGameToEdit.setMinNumberOfPlayers(boardGame.getMinNumberOfPlayers());
        boardGameToEdit.setProducer(boardGame.getProducer());
        boardGameToEdit.setBoardGameCategory(boardGame.getBoardGameCategory());

        return boardGameRepository.save(boardGameToEdit);
    }

    public void delete(Long id){
        boardGameRepository.deleteById(id);
    }
}

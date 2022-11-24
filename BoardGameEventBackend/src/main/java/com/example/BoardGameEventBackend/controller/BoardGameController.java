package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.model.BoardGame;
import com.example.BoardGameEventBackend.service.BoardGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardGameController {
    private final BoardGameService boardGameService;

    @GetMapping("/boardGames")
    public List<BoardGame> getAllBoardGames(){
        return boardGameService.getAllBoardGames();
    }

    @GetMapping("/boardGames/{id}")
    public BoardGame getBoardGame(@PathVariable Long id){
        return boardGameService.getBoardGame(id);
    }

    @PostMapping("/boardGames")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BoardGame saveBoardGame(@Valid @RequestBody BoardGame boardGame){
        return boardGameService.saveBoardGame(boardGame);
    }

    @PutMapping("/boardGames/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BoardGame updateBoardGame(@PathVariable Long id, @Valid @RequestBody BoardGame boardGame){
        return boardGameService.updateBoardGame(id, boardGame);
    }

    @DeleteMapping("/boardGames/{id}")
    public void deleteBoardGame(@PathVariable Long id){
        boardGameService.delete(id);
    }
}

package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.dto.BoardGameDto;
import com.example.BoardGameEventBackend.dto.BoardGameDtoMapper;
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
    public List<BoardGameDto> getAllBoardGames(){
        return BoardGameDtoMapper.mapToBoardGameDtos(boardGameService.getAllBoardGames());
    }

    @GetMapping("/boardGames/{id}")
    public BoardGameDto getBoardGame(@PathVariable Long id){
        return BoardGameDtoMapper.mapToBoardGameDto(boardGameService.getBoardGame(id));
    }

    @PostMapping("/boardGames")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BoardGameDto saveBoardGame(@Valid @RequestBody BoardGame boardGame){
        return BoardGameDtoMapper.mapToBoardGameDto(boardGameService.saveBoardGame(boardGame));
    }

    @PutMapping("/boardGames/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BoardGameDto updateBoardGame(@PathVariable Long id, @Valid @RequestBody BoardGame boardGame){
        return BoardGameDtoMapper.mapToBoardGameDto(boardGameService.updateBoardGame(id, boardGame));
    }

    @DeleteMapping("/boardGames/{id}")
    public void deleteBoardGame(@PathVariable Long id){
        boardGameService.delete(id);
    }
}

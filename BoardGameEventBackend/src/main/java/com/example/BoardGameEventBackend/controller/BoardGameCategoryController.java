package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.model.BoardGameCategory;
import com.example.BoardGameEventBackend.service.BoardGameCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardGameCategoryController {
    private final BoardGameCategoryService boardGameCategoryService;

    @GetMapping("/boardGamesCategories")
    public List<BoardGameCategory> getAllBoardGamesCategories(){
        return boardGameCategoryService.getAllBoardGamesCategories();
    }

    @GetMapping("/boardGamesCategories/{id}")
    public BoardGameCategory getBoardGameCategory(@PathVariable Long id){
        return boardGameCategoryService.getBoardGameCategory(id);
    }

    @PostMapping("/boardGameCategories")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BoardGameCategory saveBoardGameCategory(@Valid @RequestBody BoardGameCategory boardGameCategory){
        return boardGameCategoryService.saveBoardGameCategory(boardGameCategory);
    }

    @PutMapping("/boardGameCategories/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BoardGameCategory updateBoardGameCategory(@PathVariable Long id, @Valid @RequestBody BoardGameCategory boardGameCategory){
        return boardGameCategoryService.updateBoardGameCategory(id, boardGameCategory);
    }

    @DeleteMapping("/boardGameCategories/{id}")
    public void deleteBoardGameCategory(@PathVariable Long id){
        boardGameCategoryService.delete(id);
    }
}
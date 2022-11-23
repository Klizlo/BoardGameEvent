package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.model.BoardGameCategory;
import com.example.BoardGameEventBackend.service.BoardGameCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/boardGamesCategory/{id}")
    public BoardGameCategory getBoardGameCategory(@PathVariable Long id){
        return boardGameCategoryService.getBoardGameCategory(id);
    }

    @PostMapping("/boardGameCategory")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BoardGameCategory saveBoardGameCategory(@RequestBody BoardGameCategory boardGameCategory){
        return boardGameCategoryService.saveBoardGameCategory(boardGameCategory);
    }

    @PutMapping("/boardGameCategory/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BoardGameCategory updateBoardGameCategory(@PathVariable Long id, @RequestBody BoardGameCategory boardGameCategory){
        return boardGameCategoryService.updateBoardGameCategory(id, boardGameCategory);
    }

    @DeleteMapping("/boardGameCategory/{id}")
    public void deleteBoardGameCategory(@PathVariable Long id){
        boardGameCategoryService.delete(id);
    }
}

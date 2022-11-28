package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.dto.BoardGameCategoryDto;
import com.example.BoardGameEventBackend.dto.BoardGameCategoryDtoMapper;
import com.example.BoardGameEventBackend.model.BoardGameCategory;
import com.example.BoardGameEventBackend.service.BoardGameCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public List<BoardGameCategoryDto> getAllBoardGamesCategories(){
        return BoardGameCategoryDtoMapper.mapToBoardGameCategoryDtos(boardGameCategoryService.getAllBoardGamesCategories());
    }

    @GetMapping("/boardGamesCategories/{id}")
    public BoardGameCategoryDto getBoardGameCategory(@PathVariable Long id){
        return BoardGameCategoryDtoMapper
                .mapToBoardGameCategoryDto(boardGameCategoryService.getBoardGameCategory(id));
    }

    @PostMapping("/boardGameCategories")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public BoardGameCategoryDto saveBoardGameCategory(@Valid @RequestBody BoardGameCategory boardGameCategory){
        return BoardGameCategoryDtoMapper
                .mapToBoardGameCategoryDto(boardGameCategoryService.saveBoardGameCategory(boardGameCategory));
    }

    @PutMapping("/boardGameCategories/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BoardGameCategoryDto updateBoardGameCategory(@PathVariable Long id, @Valid @RequestBody BoardGameCategory boardGameCategory){
        return BoardGameCategoryDtoMapper
                .mapToBoardGameCategoryDto(boardGameCategoryService.updateBoardGameCategory(id, boardGameCategory));
    }

    @DeleteMapping("/boardGameCategories/{id}")
    public void deleteBoardGameCategory(@PathVariable Long id){
        boardGameCategoryService.delete(id);
    }
}

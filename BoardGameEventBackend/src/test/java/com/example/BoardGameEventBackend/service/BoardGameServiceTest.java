package com.example.BoardGameEventBackend.service;

import com.example.BoardGameEventBackend.exception.BoardGameExistsException;
import com.example.BoardGameEventBackend.exception.BoardGameNotFoundException;
import com.example.BoardGameEventBackend.model.AgeRestriction;
import com.example.BoardGameEventBackend.model.BoardGame;
import com.example.BoardGameEventBackend.model.BoardGameCategory;
import com.example.BoardGameEventBackend.model.Producer;
import com.example.BoardGameEventBackend.repository.BoardGameRepository;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardGameServiceTest {

    @Mock
    private BoardGameRepository boardGameRepository;
    @InjectMocks
    private BoardGameService boardGameService;

    @Test
    @DisplayName("Get all board games")
    @Order(1)
    public void whenGetBoardGames_returnNotEmptyList() {

        //BoardGame
        when(boardGameRepository.save(any(BoardGame.class))).thenReturn(new BoardGame());
        when(boardGameRepository.findAll()).thenReturn(List.of(new BoardGame()));

        BoardGame boardGame = new BoardGame();
        boardGame.setName("Talisman");
        boardGame.setMinNumberOfPlayers(2);
        boardGame.setMaxNumberOfPlayers(6);
        boardGame.setAgeRestriction(AgeRestriction.PLUS_FOURTEEN);
        BoardGameCategory boardGameCategory = new BoardGameCategory();
        boardGameCategory.setName("Adventure");
        boardGame.setBoardGameCategory(boardGameCategory);
        Producer producer = new Producer();
        producer.setName("Fantasy Flight Games");
        boardGame.setProducer(producer);

        boardGameRepository.save(boardGame);

        List<BoardGame> boardGames = boardGameService.getAllBoardGames();
        assertFalse(boardGames.isEmpty(), "Board game list should not be empty");
    }

    @Test
    @DisplayName("Save board game to database")
    @Order(2)
    public void givenBoardGame_whenSaveBoardGames_returnBoardGame() {
        //BoardGame
        when(boardGameRepository.save(any(BoardGame.class))).thenReturn(new BoardGame());

        BoardGame boardGame = new BoardGame();
        boardGame.setName("Dixit");
        boardGame.setMaxNumberOfPlayers(3);
        boardGame.setMaxNumberOfPlayers(8);
        boardGame.setAgeRestriction(AgeRestriction.PLUS_SEVEN);
        BoardGameCategory family = new BoardGameCategory();
        family.setName("Family");
        boardGame.setBoardGameCategory(family);
        Producer rebel = new Producer();
        rebel.setName("Rebel");
        boardGame.setProducer(rebel);

        BoardGame savedBoardGame = boardGameService.saveBoardGame(boardGame);
        assertNotNull(savedBoardGame, "Saved board game should not be null");
        assertEquals("Dixit", boardGame.getName());
    }

    @Test
    @DisplayName("Update board game in database")
    @Order(3)
    public void givenBoardGame_whenUpdateBoardGames_returnBoardGame() {

        //BoardGame
        when(boardGameRepository.save(any(BoardGame.class))).thenReturn(new BoardGame());
        when(boardGameRepository.findById(anyLong())).thenReturn(Optional.of(new BoardGame()));

        BoardGame boardGame = new BoardGame();
        boardGame.setName("Zombicide");
        boardGame.setMaxNumberOfPlayers(1);
        boardGame.setMaxNumberOfPlayers(4);
        boardGame.setAgeRestriction(AgeRestriction.PLUS_FOURTEEN);
        BoardGameCategory adventure = new BoardGameCategory();
        adventure.setName("Adventure");
        boardGame.setBoardGameCategory(adventure);
        Producer portalGames = new Producer();
        portalGames.setName("Portal Games");
        boardGame.setProducer(portalGames);

        BoardGame savedBoardGame = boardGameService.saveBoardGame(boardGame);

        Long id = getRandomLong();

        savedBoardGame.setName("Zombicide: Black Plague");
        BoardGame editedBoardGame = boardGameService.updateBoardGame(id, savedBoardGame);

        assertNotNull(editedBoardGame, "Updated board game should not be null");
        assertEquals("Zombicide: Black Plague", editedBoardGame.getName());
    }

    @Test
    @DisplayName("Update board game with existing name in database")
    @Order(4)
    public void givenBoardGame_whenUpdateBoardGames_returnException() {

        //BoardGame
        when(boardGameRepository.save(any(BoardGame.class))).thenReturn(new BoardGame());
        when(boardGameRepository.findById(anyLong())).thenReturn(Optional.of(new BoardGame()));

        BoardGame boardGame = new BoardGame();
        boardGame.setName("Star Wars: X-Wing");
        boardGame.setMaxNumberOfPlayers(2);
        boardGame.setMaxNumberOfPlayers(2);
        boardGame.setAgeRestriction(AgeRestriction.PLUS_FOURTEEN);
        BoardGameCategory strategy = new BoardGameCategory();
        boardGame.setBoardGameCategory(strategy);
        Producer rebel = new Producer();
        boardGame.setProducer(rebel);
        boardGameService.saveBoardGame(boardGame);

        BoardGame boardGame2 = new BoardGame();
        boardGame2.setName("Star Wars: Armada");
        boardGame2.setMaxNumberOfPlayers(2);
        boardGame2.setMaxNumberOfPlayers(2);
        boardGame2.setAgeRestriction(AgeRestriction.PLUS_FOURTEEN);
        boardGame2.setBoardGameCategory(strategy);
        boardGame2.setProducer(rebel);

        BoardGame savedBoardGame = boardGameService.saveBoardGame(boardGame2);

        Long id = getRandomLong();

        when(boardGameRepository.save(any(BoardGame.class)))
                .thenThrow(new BoardGameExistsException("Name " + boardGame.getName() + " is already taken"));

        savedBoardGame.setName("Star Wars: X-Wing");

        BoardGameExistsException exception = assertThrows(BoardGameExistsException.class,
                () -> boardGameService.updateBoardGame(id, savedBoardGame),
                "Updated board game should throw exception");
        assertEquals("Name " + boardGame.getName() + " is already taken", exception.getMessage());
    }

    @Test
    @DisplayName("Remove board game")
    @Order(5)
    public void givenBoardGame_whenRemoveBoardGame_returnException(){

        //BoardGame
        when(boardGameRepository.save(any(BoardGame.class))).thenReturn(new BoardGame());
        when(boardGameRepository.findById(anyLong())).thenReturn(Optional.empty());

        BoardGame boardGame = new BoardGame();
        boardGame.setName("Descent: Legends of the Dark");
        boardGame.setMaxNumberOfPlayers(1);
        boardGame.setMaxNumberOfPlayers(4);
        boardGame.setAgeRestriction(AgeRestriction.PLUS_FOURTEEN);
        BoardGameCategory adventure = new BoardGameCategory();
        boardGame.setBoardGameCategory(adventure);
        Producer ffg = new Producer();
        boardGame.setProducer(ffg);
        BoardGame savedBoardGame = boardGameService.saveBoardGame(boardGame);

        Long id = getRandomLong();

        boardGameService.delete(id);
        BoardGameNotFoundException exception = assertThrows(BoardGameNotFoundException.class,
                () -> boardGameService.getBoardGame(id),
                "Deleted board game should throw exception");
        assertEquals("Board Game " + id + " not found", exception.getMessage());
    }

    private Long getRandomLong() {
        return (long) new Random().ints(1, 10).findFirst().getAsInt();
    }

}
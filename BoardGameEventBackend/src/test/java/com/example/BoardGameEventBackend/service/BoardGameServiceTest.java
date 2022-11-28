package com.example.BoardGameEventBackend.service;

import com.example.BoardGameEventBackend.exception.BoardGameExistsException;
import com.example.BoardGameEventBackend.exception.BoardGameNotFoundException;
import com.example.BoardGameEventBackend.model.AgeRestriction;
import com.example.BoardGameEventBackend.model.BoardGame;
import com.example.BoardGameEventBackend.model.BoardGameCategory;
import com.example.BoardGameEventBackend.model.Producer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardGameServiceTest {

    @Autowired
    private BoardGameService boardGameService;
    @Autowired
    private BoardGameCategoryService boardGameCategoryService;
    @Autowired
    private ProducerService producerService;

    @Test
    @DisplayName("Get all board games")
    @Order(1)
    void givenBoardGame_whenGetBoardGames_returnNotEmptyList() {
        List<BoardGame> boardGames = boardGameService.getAllBoardGames();
        assertFalse(boardGames.isEmpty(), "Board game list should not be empty");
    }

    @Test
    @DisplayName("Save board game to database")
    @Order(2)
    void givenBoardGame_whenSaveBoardGames_returnBoardGame() {
        BoardGame boardGame = new BoardGame();
        boardGame.setName("Dixit");
        boardGame.setMaxNumberOfPlayers(3);
        boardGame.setMaxNumberOfPlayers(8);
        boardGame.setAgeRestriction(AgeRestriction.PLUS_SEVEN);
        BoardGameCategory family = boardGameCategoryService.getBoardGameCategory(3L);
        boardGame.setBoardGameCategory(family);
        Producer rebel = producerService.getProducer(3L);
        boardGame.setProducer(rebel);

        BoardGame savedBoardGame = boardGameService.saveBoardGame(boardGame);
        assertNotNull(savedBoardGame, "Saved board game should not be null");
    }

    @Test
    @DisplayName("Update board game in database")
    @Order(3)
    void givenBoardGame_whenUpdateBoardGames_returnBoardGame() {
        BoardGame boardGame = new BoardGame();
        boardGame.setName("Zombicide");
        boardGame.setMaxNumberOfPlayers(1);
        boardGame.setMaxNumberOfPlayers(4);
        boardGame.setAgeRestriction(AgeRestriction.PLUS_FOURTEEN);
        BoardGameCategory adventure = boardGameCategoryService.getBoardGameCategory(2L);
        boardGame.setBoardGameCategory(adventure);
        Producer portalGames = producerService.getProducer(4L);
        boardGame.setProducer(portalGames);

        BoardGame savedBoardGame = boardGameService.saveBoardGame(boardGame);
        savedBoardGame.setName("Zombicide: Black Plague");
        BoardGame editedBoardGame = boardGameService.saveBoardGame(savedBoardGame);
        assertNotNull(editedBoardGame, "Updated board game should not be null");
    }

    @Test
    @DisplayName("Update board game with existing name in database")
    @Order(4)
    void givenBoardGame_whenUpdateBoardGames_returnException() {
        BoardGame boardGame = new BoardGame();
        boardGame.setName("Star Wars: X-Wing");
        boardGame.setMaxNumberOfPlayers(2);
        boardGame.setMaxNumberOfPlayers(2);
        boardGame.setAgeRestriction(AgeRestriction.PLUS_FOURTEEN);
        BoardGameCategory strategy = boardGameCategoryService.getBoardGameCategory(4L);
        boardGame.setBoardGameCategory(strategy);
        Producer rebel = producerService.getProducer(3L);
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

        savedBoardGame.setName("Star Wars: X-Wing");
        BoardGameExistsException exception = assertThrows(BoardGameExistsException.class, () -> boardGameService.saveBoardGame(savedBoardGame),
                "Updated board game should throw exception");
        assertEquals("Name Star Wars: X-Wing is already taken", exception.getMessage());
    }

    @Test
    @DisplayName("Remove board game")
    @Order(5)
    void givenBoardGame_whenRemoveBoardGame_returnException(){
        BoardGame boardGame = new BoardGame();
        boardGame.setName("Descent: Legends of the Dark");
        boardGame.setMaxNumberOfPlayers(1);
        boardGame.setMaxNumberOfPlayers(4);
        boardGame.setAgeRestriction(AgeRestriction.PLUS_FOURTEEN);
        BoardGameCategory adventure = boardGameCategoryService.getBoardGameCategory(2L);
        boardGame.setBoardGameCategory(adventure);
        Producer ffg = producerService.getProducer(2L);
        boardGame.setProducer(ffg);
        BoardGame savedBoardGame = boardGameService.saveBoardGame(boardGame);

        boardGameService.delete(savedBoardGame.getId());
        BoardGameNotFoundException exception = assertThrows(BoardGameNotFoundException.class, () -> boardGameService.getBoardGame(savedBoardGame.getId()),
                "Deleted board game should throw exception");
        assertEquals("Board Game " + savedBoardGame.getId() + " not found", exception.getMessage());
    }

}
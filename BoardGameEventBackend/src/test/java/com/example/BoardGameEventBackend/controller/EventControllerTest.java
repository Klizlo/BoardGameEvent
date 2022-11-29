package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.BoardGameEventBackendApplication;
import com.example.BoardGameEventBackend.dto.EventDto;
import com.example.BoardGameEventBackend.model.*;
import com.example.BoardGameEventBackend.repository.BoardGameRepository;
import com.example.BoardGameEventBackend.repository.EventRepository;
import com.example.BoardGameEventBackend.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BoardGameEventBackendApplication.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = {"USER", "ADMIN"}, username = "Admin")
@EnableAutoConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private BoardGameRepository boardGameRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void givenEvent_whenGetEvents_thenStatus200() throws Exception {
        Event event = createEvent("Roll the dice", 5, LocalDateTime.of(2022, 9, 5, 10, 15),
                getBoardGame(2L), getUser("Adam", "Malcolm", "User1234"));


        MvcResult mvcResult = mockMvc.perform(get("/api/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andReturn();

        List<EventDto> eventDtos = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<EventDto>>(){});

        assertThat(eventDtos.get(eventDtos.size()-1)).isNotNull();
        assertThat(eventDtos.get(eventDtos.size()-1).getName()).isEqualTo(event.getName());
    }

    @Test
    @Order(2)
    public void giveId_whenGetEventById_thenStatus200() throws Exception {
        Event event = createEvent("Board game journey", 2, LocalDateTime.of(2021, 6, 25, 18, 35),
                getBoardGame(3L), getUser("John", "Smith", "SecretPassword"));

        MvcResult mvcResult = mockMvc.perform(get("/api/events/" + event.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        EventDto eventDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EventDto.class);

        assertThat(eventDto).isNotNull();
        assertThat(eventDto.getName()).isEqualTo(event.getName());
    }

    @Test
    @Order(3)
    public void whenValidInput_thenCreateEvent() throws Exception {
        Event event = new Event();
        event.setName("Night party with board games");
        event.setDate(LocalDateTime.of(2022, 5, 6, 18, 30));
        event.setNumberOfPlayers(5);
        event.setBoardGame(getBoardGame(1L));

        MvcResult mvcResult = mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        EventDto savedEvent = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EventDto.class);
        assertThat(savedEvent).isNotNull();
        assertThat(savedEvent.getName()).isEqualTo("Night party with board games");
    }

    @Test
    @Order(4)
    public void givenEvent_whenUpdateEvent_returnStatus200() throws Exception {
        Event event = createEvent("Pawns Wars", 5, LocalDateTime.of(2022, 3, 18, 20, 30),
                getBoardGame(1L), getUser("George", "Wood", "Secret"));
        event.setName("Dice Wars");
        MvcResult mvcResult = mockMvc.perform(put("/api/events/" + event.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        EventDto eventDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), EventDto.class);
        assertThat(eventDto).isNotNull();
        assertThat(eventDto.getName()).isEqualTo(event.getName());
    }

    @Test
    @Order(5)
    public void givenEvent_whenUpdateEventWithExistingInDatabaseName_returnStatus400() throws Exception {
        createEvent("GameCon", 4, LocalDateTime.of(2020, 3, 18, 20, 30),
                getBoardGame(1L), getUser("Amy", "Adams", "Secret"));
        Event event = createEvent("Fantasy Game Convention", 4, LocalDateTime.of(2020, 3, 18, 20, 30),
                getBoardGame(1L), getUser("Elisabeth", "Henderson", "<Henderson>"));
        event.setName("GameCon");
        mockMvc.perform(put("/api/events/" + event.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.msg", is("Name GameCon is already taken.")));
    }

    @Test
    @Order(6)
    public void givenUser_whenAddPlayerToEvent_returnStatus200() throws Exception {
        Event event = createEvent("Sword and Dice", 2, LocalDateTime.of(2018, 8, 19, 10, 15),
                getBoardGame(4L), getUser("Thomas", "Newby", "Adam_New"));
        User user = getUser("Mike", "Wheeler", "Eleven");

        mockMvc.perform(post("/api/events/" + event.getId() + "/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        List<User> playersByEvent = eventRepository.findPlayersByEvent(event.getId());

        assertThat(playersByEvent.isEmpty()).isEqualTo(false);
        assertThat(playersByEvent.get(playersByEvent.size()-1)).isNotNull();
        assertThat(playersByEvent.get(playersByEvent.size()-1).getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @Order(6)
    public void givenUser_whenRemovePlayerToEvent_returnStatus200() throws Exception {
        Event event = createEvent("Hit the Orc", 2, LocalDateTime.of(2018, 8, 19, 10, 15),
                getBoardGame(3L), getUser("Rebeca", "Hopper", "Unicorn123"));
        User user = getUser("David", "Bird", "ThisIsNotPassword");

        event.addPlayer(user);
        user.addEvent(event);

        eventRepository.saveAndFlush(event);

        mockMvc.perform(delete("/api/events/" + event.getId() + "/players/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        List<User> playersByEvent = eventRepository.findPlayersByEvent(event.getId());

        assertThat(playersByEvent.isEmpty()).isEqualTo(true);
    }

    @Test
    @Order(7)
    public void givenEvent_whenDeleteEvent_returnStatus200() throws Exception {
        Event event = createEvent("Roll 'Em All", 2, LocalDateTime.of(2018, 8, 19, 10, 15),
                getBoardGame(3L), getUser("Paul", "Rodger", "RodgerRodger"));

        mockMvc.perform(delete("/api/events/" + event.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(eventRepository.findById(event.getId())).isEmpty();

    }

    private BoardGame getBoardGame(Long id) {
        BoardGame foundBoardGame = boardGameRepository.findById(id).orElseThrow();
        BoardGame boardGame = new BoardGame();
        boardGame.setId(foundBoardGame.getId());
        boardGame.setName(foundBoardGame.getName());
        boardGame.setMinNumberOfPlayers(foundBoardGame.getMinNumberOfPlayers());
        boardGame.setMaxNumberOfPlayers(foundBoardGame.getMaxNumberOfPlayers());
        boardGame.setAgeRestriction(foundBoardGame.getAgeRestriction());
        Producer producer = new Producer();
        producer.setId(foundBoardGame.getProducer().getId());
        producer.setName(foundBoardGame.getProducer().getName());
        boardGame.setProducer(producer);
        BoardGameCategory boardGameCategory = new BoardGameCategory();
        boardGameCategory.setId(foundBoardGame.getBoardGameCategory().getId());
        boardGameCategory.setName(foundBoardGame.getBoardGameCategory().getName());
        boardGame.setBoardGameCategory(boardGameCategory);
        return boardGame;
    }

    private User getUser(String username, String email, String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        User savedUser = userRepository.save(user);

        user.setId(savedUser.getId());
        return user;
    }

    private Event createEvent(String name, int numberOfPlayers, LocalDateTime date, BoardGame boardGame, User organizer){
        Event event = new Event();
        event.setName(name);
        event.setNumberOfPlayers(numberOfPlayers);
        event.setDate(date);
        event.setBoardGame(boardGame);
        event.setOrganizer(organizer);
        return eventRepository.saveAndFlush(event);
    }

}

package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.BoardGameEventBackendApplication;
import com.example.BoardGameEventBackend.dto.ProducerDto;
import com.example.BoardGameEventBackend.model.*;
import com.example.BoardGameEventBackend.repository.ProducerRepository;
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

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BoardGameEventBackendApplication.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = {"USER", "ADMIN"}, username = "Admin")
@EnableAutoConfiguration
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProducerRepository producerRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void givenProducer_whenGetProducers_thenStatus200() throws Exception {
        Producer producer = createProducer("Asmodee Editions");

        MvcResult mvcResult = mockMvc.perform(get("/api/producers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andReturn();

        List<ProducerDto> producerDtos = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<ProducerDto>>() {});

        assertThat(producerDtos.get(producerDtos.size()-1)).isNotNull();
        assertThat(producerDtos.get(producerDtos.size()-1).getName()).isEqualTo(producer.getName());
    }

    @Test
    @Order(2)
    public void giveId_whenGetProducerById_thenStatus200() throws Exception {
        Producer producer = createProducer("Goliath B.V.");

        MvcResult mvcResult = mockMvc.perform(get("/api/producers/" + producer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        ProducerDto producerDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProducerDto.class);

        assertThat(producerDto).isNotNull();
        assertThat(producerDto.getName()).isEqualTo(producer.getName());
    }

    @Test
    @Order(3)
    public void whenValidInput_thenCreateProducer() throws Exception {
        Producer producer = new Producer();
        producer.setName("Grand Prix International");

        MvcResult mvcResult = mockMvc.perform(post("/api/producers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producer)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        ProducerDto producerDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProducerDto.class);
        assertThat(producerDto).isNotNull();
        assertThat(producerDto.getName()).isEqualTo("Grand Prix International");
    }

    @Test
    @Order(4)
    public void givenProducer_whenUpdateProducer_returnStatus200() throws Exception {
        Producer producer = createProducer("Hasbro");
        producer.setName("Ravensburger");
        MvcResult mvcResult = mockMvc.perform(put("/api/producers/" + producer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producer)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        ProducerDto producerDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProducerDto.class);
        assertThat(producerDto).isNotNull();
        assertThat(producerDto.getName()).isEqualTo(producer.getName());
    }

    @Test
    @Order(5)
    public void givenProducer_whenUpdateProducerWithExistingInDatabaseName_returnStatus400() throws Exception {
        createProducer("Test1");
        Producer producer = createProducer("Test2");
        producer.setName("Test1");
        mockMvc.perform(put("/api/producers/" + producer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producer)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.msg", is("Name Test1 is already used.")));
    }

    @Test
    @Order(6)
    public void givenEvent_whenDeleteEvent_returnStatus200() throws Exception {
        Producer producer = createProducer("Test3");
        mockMvc.perform(delete("/api/producers/" + producer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(producerRepository.findById(producer.getId())).isEmpty();

    }

    private Producer createProducer(String name){
        Producer producer = new Producer();
        producer.setName(name);
        return producerRepository.saveAndFlush(producer);
    }

}

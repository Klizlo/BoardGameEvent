package com.example.BoardGameEventBackend.controller;

import com.example.BoardGameEventBackend.BoardGameEventBackendApplication;
import com.example.BoardGameEventBackend.dto.RoleDto;
import com.example.BoardGameEventBackend.model.*;
import com.example.BoardGameEventBackend.repository.RoleRepository;
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
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void givenRole_whenGetRoles_thenStatus200() throws Exception {
        Role role = createRole("GAMER");

        MvcResult mvcResult = mockMvc.perform(get("/api/roles")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andReturn();

        List<RoleDto> roleDtos = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<RoleDto>>() {});

        assertThat(roleDtos.get(roleDtos.size()-1)).isNotNull();
        assertThat(roleDtos.get(roleDtos.size()-1).getName()).isEqualTo(role.getName());
    }

    @Test
    @Order(2)
    public void giveId_whenGetRoleById_thenStatus200() throws Exception {
        Role role = createRole("GAME_MASTER");

        MvcResult mvcResult = mockMvc.perform(get("/api/roles/" + role.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        RoleDto roleDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RoleDto.class);

        assertThat(roleDto).isNotNull();
        assertThat(roleDto.getName()).isEqualTo(role.getName());
    }

    @Test
    @Order(3)
    public void whenValidInput_thenCreateRole() throws Exception {
        Role role = new Role();
        role.setName("DATABASE_ADMIN");

        MvcResult mvcResult = mockMvc.perform(post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(role)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        RoleDto savedRole = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RoleDto.class);
        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getName()).isEqualTo("DATABASE_ADMIN");
    }

    @Test
    @Order(4)
    public void givenRole_whenUpdateRole_returnStatus200() throws Exception {
        Role role = createRole("WATCHER");
        role.setName("VIEWER");
        MvcResult mvcResult = mockMvc.perform(put("/api/roles/" + role.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(role)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        RoleDto roleDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RoleDto.class);
        assertThat(roleDto).isNotNull();
        assertThat(roleDto.getName()).isEqualTo(role.getName());
    }

    @Test
    @Order(5)
    public void givenRole_whenUpdateRoleWithExistingInDatabaseName_returnStatus400() throws Exception {
        createRole("TEST_ROLE");
        Role role = createRole("TEST_ROLE2");
        role.setName("TEST_ROLE");
        mockMvc.perform(put("/api/roles/" + role.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(role)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.msg", is("Role TEST_ROLE already exists")));
    }

    @Test
    @Order(6)
    public void givenEvent_whenDeleteEvent_returnStatus200() throws Exception {
        Role role = createRole("TEST");
        mockMvc.perform(delete("/api/roles/" + role.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(roleRepository.findById(role.getId())).isEmpty();

    }

    private Role createRole(String name){
        Role role = new Role();
        role.setName(name);
        return roleRepository.saveAndFlush(role);
    }

}

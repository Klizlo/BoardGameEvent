package com.example.BoardGameEventBackend.service;

import com.example.BoardGameEventBackend.exception.UserExistsException;
import com.example.BoardGameEventBackend.exception.UserNotFoundException;
import com.example.BoardGameEventBackend.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@PropertySource("application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Get all users")
    @Order(1)
    void givenUser_whenGetUsers_returnNotEmptyList(){
        User user = new User();
        user.setUsername("User");
        user.setEmail("user@user.com");
        user.setPassword("User1234");
        userService.saveUser(user);

        List<User> allUsers = userService.getAllUsers();
        assertFalse(allUsers.isEmpty());
    }

    @Test
    @DisplayName("Save user to database")
    @Order(2)
    void givenUser_whenAddUser_ReturnUser(){
        User user = new User();
        user.setUsername("User1");
        user.setEmail("user1@user.com");
        user.setPassword("User1234");

        User savedUser = userService.saveUser(user);
        assertNotNull(savedUser, "User should not be null");
    }

    @Test
    @DisplayName("Update user in database")
    @Order(3)
    void givenUser_whenUpdateUser_ReturnUser(){
        User user = new User();
        user.setUsername("User2");
        user.setEmail("user2@user.com");
        user.setPassword("User1234");

        User savedUser = userService.saveUser(user);

        savedUser.setUsername("newUsername");
        User editedUser = userService.updateUser(savedUser.getId(), savedUser);
        assertNotNull(editedUser, "User should not be null");
    }

    @Test
    @DisplayName("Update user with existing username in database")
    @Order(4)
    void givenUser_whenUpdateUser_ReturnException(){
        User user = new User();
        user.setUsername("User3");
        user.setEmail("user3@user.com");
        user.setPassword("User1234");

        userService.saveUser(user);

        User user2 = new User();
        user2.setUsername("User4");
        user2.setEmail("user4@user.com");
        user2.setPassword("User1234");

        User savedUser = userService.saveUser(user2);

        savedUser.setUsername("User3");
        UserExistsException exception = assertThrows(UserExistsException.class,
                () -> userService.updateUser(savedUser.getId(), savedUser));
        assertEquals("Username User3 is already taken", exception.getMessage());
    }

    @Test
    @DisplayName("Delete user")
    @Order(5)
    void givenStudent_whenRemoveUser_givenException(){
        User user = new User();
        user.setUsername("User5");
        user.setEmail("user5@user.com");
        user.setPassword("User1234");

        User savedUser = userService.saveUser(user);

        userService.delete(user.getId());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.getUser(savedUser.getId()));
        assertEquals("User " + savedUser.getId() + " not found", exception.getMessage());

    }

}

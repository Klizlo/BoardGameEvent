package com.example.BoardGameEventBackend.service;

import com.example.BoardGameEventBackend.config.TestConfig;
import com.example.BoardGameEventBackend.exception.UserExistsException;
import com.example.BoardGameEventBackend.exception.UserNotFoundException;
import com.example.BoardGameEventBackend.model.Role;
import com.example.BoardGameEventBackend.model.User;
import com.example.BoardGameEventBackend.repository.RoleRepository;
import com.example.BoardGameEventBackend.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@Import({TestConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Get all users")
    @Order(1)
    public void givenUser_whenGetUsers_returnNotEmptyList(){

        when(roleRepository.save(any(Role.class))).thenReturn(new Role());
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(userRepository.findAll()).thenReturn(List.of(new User()));
        User user = new User();
        user.setUsername("User");
        user.setEmail("user@user.com");
        user.setPassword("User1234");
        userService.saveUser(user);

        List<User> allUsers = userService.getAllUsers();
        assertFalse(allUsers.isEmpty(), "User lists should not be empty");
    }

    @Test
    @DisplayName("Save user to database")
    @Order(2)
    public void givenUser_whenAddUser_ReturnUser(){
        when(roleRepository.save(any(Role.class))).thenReturn(new Role());
        when(userRepository.save(any(User.class))).thenReturn(new User());
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
    public void givenUser_whenUpdateUser_ReturnUser(){
        when(roleRepository.save(any(Role.class))).thenReturn(new Role());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(userRepository.save(any(User.class))).thenReturn(new User());

        User user = new User();
        user.setUsername("User2");
        user.setEmail("user2@user.com");
        user.setPassword("User1234");

        User savedUser = userService.saveUser(user);

        savedUser.setUsername("newUsername");
        User editedUser = userService.updateUser(getRandomLong(), savedUser);
        assertNotNull(editedUser, "User should not be null");
    }

    @Test
    @DisplayName("Update user with existing username in database")
    @Order(4)
    public void givenUser_whenUpdateUser_ReturnException(){
        when(roleRepository.save(any(Role.class))).thenReturn(new Role());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(userRepository.save(any(User.class))).thenReturn(new User());

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
                () -> userService.updateUser(getRandomLong(), savedUser));
        assertEquals("Username User3 is already taken", exception.getMessage());
    }

    @Test
    @DisplayName("Delete user")
    @Order(5)
    public void givenStudent_whenRemoveUser_returnException(){
        User user = new User();
        user.setUsername("User5");
        user.setEmail("user5@user.com");
        user.setPassword("User1234");

        User savedUser = userService.saveUser(user);

        userService.delete(user.getId());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.getUser(savedUser.getId()));
        assertEquals("User " + savedUser.getId() + " not found", exception.getMessage());

    }

    private Long getRandomLong() {
        return (long) new Random().ints(1, 10).findFirst().getAsInt();
    }

}

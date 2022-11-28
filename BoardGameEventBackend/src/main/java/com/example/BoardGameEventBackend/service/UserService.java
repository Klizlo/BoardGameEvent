package com.example.BoardGameEventBackend.service;

import com.example.BoardGameEventBackend.exception.ForbiddenException;
import com.example.BoardGameEventBackend.exception.UserNotFoundException;
import com.example.BoardGameEventBackend.model.Event;
import com.example.BoardGameEventBackend.model.Role;
import com.example.BoardGameEventBackend.model.User;
import com.example.BoardGameEventBackend.exception.UserExistsException;
import com.example.BoardGameEventBackend.repository.RoleRepository;
import com.example.BoardGameEventBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service(value = "UserService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(Long id) throws ForbiddenException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Transactional
    public User saveUser(User user) throws UserExistsException {

        if(userRepository.existsByUsername(user.getUsername())){
            throw new UserExistsException("Username " + user.getUsername() + " is already taken");
        }

        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserExistsException("Email " + user.getEmail() + " is already taken");
        }

        if(user.getRoles().isEmpty()) {
            user.addRole(roleRepository
                    .findByName("USER")
                    .orElseGet(() -> roleRepository.save(new Role("USER"))));
        }

        user.getRoles().forEach(role -> role.addUser(user));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, User user) throws ForbiddenException, UserExistsException {

        User userToEdit = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));

        if(userRepository.existsByUsername(user.getUsername()) && !user.getUsername().equals(userToEdit.getUsername())){
            throw new UserExistsException("Username " + user.getUsername() + " is already taken");
        }

        if(userRepository.existsByEmail(user.getEmail()) && !user.getEmail().equals(userToEdit.getEmail())){
            throw new UserExistsException("Email " + user.getEmail() + " is already taken");
        }

        userToEdit.setUsername(user.getUsername());
        userToEdit.setEmail(user.getEmail());

        userToEdit.getRoles().stream()
                .filter(roleToEdit -> !user.getRoles().contains(roleToEdit))
                .forEach(roleToEdit -> roleToEdit.removeUser(user));
        userToEdit.setRoles(user.getRoles());

        return userRepository.save(userToEdit);
    }

    public List<Event> getUserEvents(Long id){
        return userRepository.findEventsByUser(id);
    }

    public void delete(Long id) throws ForbiddenException {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}

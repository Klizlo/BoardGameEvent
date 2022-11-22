package com.example.BoardGameEventBackend.service;

import com.example.BoardGameEventBackend.exception.UserNotFoundException;
import com.example.BoardGameEventBackend.model.Role;
import com.example.BoardGameEventBackend.model.User;
import com.example.BoardGameEventBackend.exception.UserExistsException;
import com.example.BoardGameEventBackend.repository.RoleRepository;
import com.example.BoardGameEventBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "UserService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User saveUser(User user){

        if(userRepository.existsByUsername(user.getUsername())){
            throw new UserExistsException("Username " + user.getUsername() + " is already taken");
        }

        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserExistsException("Email " + user.getEmail() + " is already taken");
        }

        user.addRole(roleRepository
                .findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role("USER"))));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User userToEdit = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));

        if(userRepository.existsByUsername(user.getUsername())){
            throw new UserExistsException("Username " + user.getUsername() + " is already taken");
        }

        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserExistsException("Email " + user.getEmail() + " is already taken");
        }

        userToEdit.setUsername(user.getUsername());
        userToEdit.setEmail(user.getEmail());
        userToEdit.setPassword(user.getPassword());

        return userRepository.save(userToEdit);
    }

    public User addRolesToUser(Long id, List<Role> roles) {
        User userToEdit = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));

        List<Role> rolesForUser = roleRepository.findAllByNameIn(roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList()));

        rolesForUser.forEach(userToEdit::addRole);

        return userRepository.save(userToEdit);
    }

    public void delete(Long id) {
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

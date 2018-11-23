package com.exchange.exchange.controllers;

import com.exchange.exchange.exception.ResourceNotFoundException;
import com.exchange.exchange.model.User;
import com.exchange.exchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Long usersId) {
        return userRepository.findById(usersId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", usersId));
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId,
                           @Valid @RequestBody User userDetails) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setAlias(userDetails.getAlias());
        user.setEmail(userDetails.getEmail());
        user.setPass(userDetails.getPass());
        user.setExternal(userDetails.getExternal());


        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUsers(@PathVariable(value = "id") Long userId) {
        User users = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepository.delete(users);

        return ResponseEntity.ok().build();
    }
}
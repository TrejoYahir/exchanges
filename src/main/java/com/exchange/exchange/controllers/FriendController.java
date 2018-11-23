package com.exchange.exchange.controllers;

import com.exchange.exchange.exception.ResourceNotFoundException;
import com.exchange.exchange.model.Friend;
import com.exchange.exchange.repository.FriendRepository;
import com.exchange.exchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FriendController {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{userId}/friends")
    public List<Friend> getAllFriendsByUserId(@PathVariable (value = "userId") Long userId) {
        return friendRepository.findAllByUser1(userId);
    }

    @PostMapping("/users/{userId}/friends")
    public Friend createFriend(@PathVariable (value = "userId") Long userId,
                               @Valid @RequestBody Friend friend) {
        Friend friend2 = new Friend();
        friend2.setUser1(friend.getUser2());
        return userRepository.findById(userId).map(user -> {
            friend.setUser1(user);
            friend2.setUser2(user);
            friendRepository.save(friend2);
            return friendRepository.save(friend);
        }).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @PutMapping("/users/{userId}/friends/{friendId}")
    public Friend updateFriend(@PathVariable (value = "userId") Long userId,
                               @PathVariable (value = "friendId") Long friendId,
                               @Valid @RequestBody Friend friendRequest) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }

        return friendRepository.findById(friendId).map(friend -> {
            friend.setUser2(friendRequest.getUser2());
            return friendRepository.save(friend);
        }).orElseThrow(() -> new ResourceNotFoundException("Friend", "id", friendId));
    }

    @DeleteMapping("/users/{userId}/friends/{friendId}")
    public ResponseEntity<?> deleteFriend(@PathVariable (value = "userId") Long userId,
                                          @PathVariable (value = "friendId") Long friendId) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }

        return friendRepository.findById(friendId).map(friend -> {
            friendRepository.delete(friend);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Friend", "id", friendId));
    }
}

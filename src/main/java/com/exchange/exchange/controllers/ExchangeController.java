package com.exchange.exchange.controllers;

import com.exchange.exchange.exception.ResourceNotFoundException;
import com.exchange.exchange.model.Exchange;
import com.exchange.exchange.repository.ExchangeRepository;
import com.exchange.exchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExchangeController {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{userId}/exchanges")
    public List<Exchange> getAllExchangesByUserId(@PathVariable (value = "userId") Long userId) {
        return exchangeRepository.findAllByCreatorId(userId);
    }

    @PostMapping("/users/{userId}/exchanges")
    public Exchange createExchange(@PathVariable (value = "userId") Long userId,
                               @Valid @RequestBody Exchange exchange) {
        return userRepository.findById(userId).map(user -> {
            exchange.setCreator(user);
            return exchangeRepository.save(exchange);
        }).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @PutMapping("/users/{userId}/exchanges/{exchangeId}")
    public Exchange updateExchange(@PathVariable (value = "userId") Long userId,
                               @PathVariable (value = "exchangeId") Long exchangeId,
                               @Valid @RequestBody Exchange exchangeRequest) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }

        return exchangeRepository.findById(exchangeId).map(exchange -> {
            exchange.setCreator(exchangeRequest.getCreator());
            return exchangeRepository.save(exchange);
        }).orElseThrow(() -> new ResourceNotFoundException("Exchange", "id", exchangeId));
    }

    @DeleteMapping("/users/{userId}/exchanges/{exchangeId}")
    public ResponseEntity<?> deleteExchange(@PathVariable (value = "userId") Long userId,
                                          @PathVariable (value = "exchangeId") Long exchangeId) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }

        return exchangeRepository.findById(exchangeId).map(exchange -> {
            exchangeRepository.delete(exchange);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Exchange", "id", exchangeId));
    }
}

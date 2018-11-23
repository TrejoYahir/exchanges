package com.exchange.exchange.controllers;

import com.exchange.exchange.exception.ResourceNotFoundException;
import com.exchange.exchange.model.Pair;
import com.exchange.exchange.repository.ExchangeRepository;
import com.exchange.exchange.repository.PairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PairController {

    @Autowired
    private PairRepository pairRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @GetMapping("/users/{exchangeId}/pairs")
    public List<Pair> getAllPairsByExchangeId(@PathVariable (value = "exchangeId") Long exchangeId) {
        return pairRepository.findAllByExchange(exchangeId);
    }

    @PostMapping("/users/{exchangeId}/pairs")
    public List<Pair> createPair(@PathVariable (value = "exchangeId") Long exchangeId,
                           @Valid @RequestBody List<Pair> pairs) {
        return exchangeRepository.findById(exchangeId).map(exchange -> {
            for(Pair p : pairs) {
                p.setExchange(exchange);
                pairRepository.save(p);
            }
            return pairRepository.findAllByExchange(exchangeId);
        }).orElseThrow(() -> new ResourceNotFoundException("Exchange", "id", exchangeId));
    }

    @PutMapping("/users/{exchangeId}/pairs/{pairId}")
    public Pair updatePair(@PathVariable (value = "exchangeId") Long exchangeId,
                           @PathVariable (value = "pairId") Long pairId,
                           @Valid @RequestBody Pair pairRequest) {
        if(!exchangeRepository.existsById(exchangeId)) {
            throw new ResourceNotFoundException("Exchange", "id", exchangeId);
        }

        return pairRepository.findById(pairId).map(pair -> {
            pair.setUser1(pairRequest.getUser1());
            pair.setUser2(pairRequest.getUser2());
            return pairRepository.save(pair);
        }).orElseThrow(() -> new ResourceNotFoundException("Pair", "id", pairId));
    }

    @DeleteMapping("/users/{exchangeId}/pairs/{pairId}")
    public ResponseEntity<?> deletePair(@PathVariable (value = "exchangeId") Long exchangeId,
                                        @PathVariable (value = "pairId") Long pairId) {
        if(!exchangeRepository.existsById(exchangeId)) {
            throw new ResourceNotFoundException("Exchange", "id", exchangeId);
        }

        return pairRepository.findById(pairId).map(pair -> {
            pairRepository.delete(pair);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Pair", "id", pairId));
    }

    @DeleteMapping("/users/{exchangeId}/pairs/")
    public ResponseEntity<?> deletePairs(@PathVariable (value = "exchangeId") Long exchangeId) {
        if(!exchangeRepository.existsById(exchangeId)) {
            throw new ResourceNotFoundException("Exchange", "id", exchangeId);
        }
        pairRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
}

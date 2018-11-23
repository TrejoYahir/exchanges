package com.exchange.exchange.controllers;

import com.exchange.exchange.exception.ResourceNotFoundException;
import com.exchange.exchange.model.Participant;
import com.exchange.exchange.repository.ExchangeRepository;
import com.exchange.exchange.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @GetMapping("/users/{exchangeId}/participants")
    public List<Participant> getAllParticipantsByExchangeId(@PathVariable (value = "exchangeId") Long exchangeId) {
        return participantRepository.findAllByExchange(exchangeId);
    }

    @PostMapping("/users/{exchangeId}/participants")
    public List<Participant> createParticipant(@PathVariable (value = "exchangeId") Long exchangeId,
                                 @Valid @RequestBody List<Participant> participants) {
        return exchangeRepository.findById(exchangeId).map(exchange -> {
            for(Participant p : participants) {
                p.setExchange(exchange);
                participantRepository.save(p);
            }
            return participantRepository.findAllByExchange(exchangeId);
        }).orElseThrow(() -> new ResourceNotFoundException("Exchange", "id", exchangeId));
    }

    @PutMapping("/users/{exchangeId}/participants/{participantId}")
    public Participant updateParticipant(@PathVariable (value = "exchangeId") Long exchangeId,
                           @PathVariable (value = "participantId") Long participantId,
                           @Valid @RequestBody Participant participantRequest) {
        if(!exchangeRepository.existsById(exchangeId)) {
            throw new ResourceNotFoundException("Exchange", "id", exchangeId);
        }

        return participantRepository.findById(participantId).map(participant -> {
            participant.setUser(participantRequest.getUser());
            participant.setTheme(participantRequest.getTheme());
            participant.setInGroup(participantRequest.getIsInGroup());
            participant.setAcceptInvite(participantRequest.getAcceptInvite());
            return participantRepository.save(participant);
        }).orElseThrow(() -> new ResourceNotFoundException("Participant", "id", participantId));
    }

    @DeleteMapping("/users/{exchangeId}/participants/{participantId}")
    public ResponseEntity<?> deleteParticipant(@PathVariable (value = "exchangeId") Long exchangeId,
                                        @PathVariable (value = "participantId") Long participantId) {
        if(!exchangeRepository.existsById(exchangeId)) {
            throw new ResourceNotFoundException("Exchange", "id", exchangeId);
        }

        return participantRepository.findById(participantId).map(participant -> {
            participantRepository.delete(participant);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Participant", "id", participantId));
    }

    @DeleteMapping("/users/{exchangeId}/participants/")
    public ResponseEntity<?> deleteParticipants(@PathVariable (value = "exchangeId") Long exchangeId) {
        if(!exchangeRepository.existsById(exchangeId)) {
            throw new ResourceNotFoundException("Exchange", "id", exchangeId);
        }
        participantRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
}

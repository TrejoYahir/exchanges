package com.exchange.exchange.controllers;

import com.exchange.exchange.exception.ResourceNotFoundException;
import com.exchange.exchange.model.Theme;
import com.exchange.exchange.repository.ExchangeRepository;
import com.exchange.exchange.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ThemeController {

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    @GetMapping("/users/{exchangeId}/themes")
    public List<Theme> getAllThemesByExchangeId(@PathVariable (value = "exchangeId") Long exchangeId) {
        return themeRepository.findAllByExchange(exchangeId);
    }

    @PostMapping("/users/{exchangeId}/themes")
    public List<Theme> createTheme(@PathVariable (value = "exchangeId") Long exchangeId,
                                               @Valid @RequestBody List<Theme> themes) {
        return exchangeRepository.findById(exchangeId).map(exchange -> {
            for(Theme p : themes) {
                p.setExchange(exchange);
                themeRepository.save(p);
            }
            return themeRepository.findAllByExchange(exchangeId);
        }).orElseThrow(() -> new ResourceNotFoundException("Exchange", "id", exchangeId));
    }

    @PutMapping("/users/{exchangeId}/themes/{themeId}")
    public Theme updateTheme(@PathVariable (value = "exchangeId") Long exchangeId,
                                         @PathVariable (value = "themeId") Long themeId,
                                         @Valid @RequestBody Theme themeRequest) {
        if(!exchangeRepository.existsById(exchangeId)) {
            throw new ResourceNotFoundException("Exchange", "id", exchangeId);
        }

        return themeRepository.findById(themeId).map(theme -> {
            theme.setThemeName(themeRequest.getThemeName());
            return themeRepository.save(theme);
        }).orElseThrow(() -> new ResourceNotFoundException("Theme", "id", themeId));
    }

    @DeleteMapping("/users/{exchangeId}/themes/{themeId}")
    public ResponseEntity<?> deleteTheme(@PathVariable (value = "exchangeId") Long exchangeId,
                                               @PathVariable (value = "themeId") Long themeId) {
        if(!exchangeRepository.existsById(exchangeId)) {
            throw new ResourceNotFoundException("Exchange", "id", exchangeId);
        }

        return themeRepository.findById(themeId).map(theme -> {
            themeRepository.delete(theme);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Theme", "id", themeId));
    }

    @DeleteMapping("/users/{exchangeId}/themes/")
    public ResponseEntity<?> deleteThemes(@PathVariable (value = "exchangeId") Long exchangeId) {
        if(!exchangeRepository.existsById(exchangeId)) {
            throw new ResourceNotFoundException("Exchange", "id", exchangeId);
        }
        themeRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
}

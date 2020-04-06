package com.example.demo.controller;

import java.util.List;
import java.util.Random;

import com.example.demo.entity.Character;
import com.example.demo.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RickAndMortyController {
    @Autowired
    private CharacterRepository characterRepository;

    @GetMapping("/random")
    public Character getRandomCharacter() {
        Random random = new Random();
        int characterId = random.nextInt(characterRepository.numberOfCharacters());
        return characterRepository.findById((long) characterId).get();
    }

    @GetMapping("/characters")
    public List<Character> findCharactersByName (@RequestParam String name) {
        return characterRepository.findCharactersByName(name);
    }
}

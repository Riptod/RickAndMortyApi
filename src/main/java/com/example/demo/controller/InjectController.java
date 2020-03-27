package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import com.example.demo.entity.Character;
import com.example.demo.exception.LoadDataException;
import com.example.demo.repository.CharacterRepository;
import com.example.demo.utill.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InjectController {
    @Autowired
    private JsonReader jsonReader;
    @Autowired
    private CharacterRepository characterRepository;

    @PostConstruct
    public void injectCharacters() {
        try {
            Long nums = jsonReader.numberOfPage(jsonReader
                    .readJsonFromUrl("https://rickandmortyapi.com/api/character/"));
            System.out.println(nums);
            List<Character> result = new ArrayList<>();
            for (int i = 1; i < nums + 1; i++) {
                result.addAll(jsonReader.getArray(jsonReader
                        .readJsonFromUrl("https://rickandmortyapi.com/api/character/?page=" + i)));
            }
            characterRepository.saveAll(result);
        } catch (IOException e) {
            throw new LoadDataException("Can`t load data to DB");
        }

    }
}

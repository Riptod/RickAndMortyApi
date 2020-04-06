package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import com.example.demo.entity.Character;
import com.example.demo.exception.LoadDataException;
import com.example.demo.repository.CharacterRepository;
import com.example.demo.utill.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InjectController {
    @Autowired
    private JsonService jsonService;
    @Autowired
    private CharacterRepository characterRepository;
    @Value("${rickmorty.page.url}")
    String pageUrl;
    @Value("${rickmorty.url}")
    String url;
    @PostConstruct
    public void injectCharacters() {
        try {
            Long nums = jsonService.numberOfPage(jsonService
                    .readJsonFromUrl(url));
            List<Character> result = new ArrayList<>();
            for (int i = 1; i <= nums; i++) {
                result.addAll(jsonService.getObjectsFromJson(jsonService
                        .readJsonFromUrl(pageUrl + i)));
            }
            characterRepository.saveAll(result);
        } catch (IOException e) {
            throw new LoadDataException("Can`t load data to DB");
        }

    }
}

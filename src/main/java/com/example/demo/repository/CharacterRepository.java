package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    @Query(value = "select COUNT(id) from characters", nativeQuery = true)
    Integer numberOfCharacters();

    @Query(value = "select * from characters where name like %?1%", nativeQuery = true)
    List<Character> findCharactersByName(String name);
}

package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.horse.HorseDTO;
import com.example.travanalysserver.entity.testing.Horse;
import com.example.travanalysserver.repository.HorseRepo;
import com.example.travanalysserver.service.interfaces.HorseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/horses")
public class HorseController {

    private final HorseService horseService;
    private final HorseRepo horseRepo;

    @GetMapping("/{id}")
    public HorseDTO getHorseById(@PathVariable Long id) {
        return horseService.getHorseById(id);
    }

    @GetMapping
    public List<Horse> getAllHorses() {
        return horseRepo.findAll();  // Fetch all horses as DTOs
    }

    @PostMapping("/send")
    public ResponseEntity<String> addHorse(@RequestBody Horse horse) {
        horseRepo.save(horse);
        return new ResponseEntity<>("Horse added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/send2/{name}")
    public ResponseEntity<String> addHorse(@PathVariable String name) {
        System.out.println("Metoden fungerar");
        horseRepo.save(new Horse(null, name));
        return new ResponseEntity<>("Horse added successfully", HttpStatus.CREATED);
    }
}

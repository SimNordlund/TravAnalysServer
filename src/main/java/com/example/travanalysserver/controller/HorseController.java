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
        return horseRepo.findAll();
    }

    @PostMapping("/send")
    public ResponseEntity<String> addHorse(@RequestBody Horse horse) {
        horseRepo.save(horse);
        return new ResponseEntity<>("Horse added successfully", HttpStatus.CREATED);
    }

 /*   @DeleteMapping("/delete")
    public ResponseEntity<String> deleteHorse(@RequestBody Long horseId) {
        Horse horseToDelete = horseRepo.findById(horseId).orElse(null);
        if (horseToDelete == null) {
            return new ResponseEntity<>("Horse not found", HttpStatus.NOT_FOUND);
        }

        horseRepo.delete(horseToDelete);

        return new ResponseEntity<>("Horse deleted successfully", HttpStatus.OK);
    } */

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteHorse(@RequestParam Long horseId) {
        if (!horseRepo.existsById(horseId)) {
            return new ResponseEntity<>("Horse not found", HttpStatus.NOT_FOUND);
        }

        horseRepo.deleteById(horseId);
        return new ResponseEntity<>("Horse deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/send2/{name}")
    public ResponseEntity<String> addHorse(@PathVariable String name) {
        System.out.println("Metoden fungerar");
        horseRepo.save(new Horse(null, name));
        return new ResponseEntity<>("Horse added successfully", HttpStatus.CREATED);
    }
}

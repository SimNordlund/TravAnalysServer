package com.example.travanalysserver.controller;

import com.example.travanalysserver.entity.ReducedSystem;
import com.example.travanalysserver.repository.ReducedSystemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ReducedSystemController {

    private final ReducedSystemRepo reducedSystemRepo;

    @GetMapping("/s1")
    public Optional<ReducedSystem> getSystemOne (@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }
    @GetMapping("/s2")
    public Optional<ReducedSystem> getSystemTwo (@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }
    @GetMapping("/s3")
    public Optional<ReducedSystem> getSystemThree (@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }
    @GetMapping("/s4")
    public Optional<ReducedSystem> getSystemFour (@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }
    @GetMapping("/s5")
    public Optional<ReducedSystem> getSystemFive (@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }


    @PostMapping("/reduced/system")
    public ResponseEntity<String> addReducedSystem(@RequestBody ReducedSystem reducedSystem) {
        reducedSystemRepo.save(reducedSystem);
        return new ResponseEntity<>("Reduced system added successfully", HttpStatus.CREATED);
    }
}
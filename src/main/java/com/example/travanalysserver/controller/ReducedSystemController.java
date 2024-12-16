package com.example.travanalysserver.controller;

import com.example.travanalysserver.entity.ReducedSystem;
import com.example.travanalysserver.repository.ReducedSystemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor

public class ReducedSystemController {

    private final ReducedSystemRepo reducedSystemRepo;

    @GetMapping("/s1")
        public Optional<ReducedSystemRepo> getSystemOne (@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }
    @GetMapping("/s2")
    public Optional<ReducedSystemRepo> getSystemTwo (@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }
    @GetMapping("/s3")
    public Optional<ReducedSystemRepo> getSystemThree (@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }
    @GetMapping("/s4")
    public Optional<ReducedSystemRepo> getSystemFour (@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }
    @GetMapping("/s5")
    public Optional<ReducedSystemRepo> getSystemFive (@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }
}
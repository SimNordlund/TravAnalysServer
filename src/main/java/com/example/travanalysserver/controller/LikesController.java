package com.example.travanalysserver.controller;

import com.example.travanalysserver.repository.LikesRepo;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikesController {

    /* private final LikesRepo repo;

    @GetMapping
    public Map<String,Integer> get() {
        return Map.of("total", repo.findById(1L).orElseThrow().getTotal());
    }

    @PostMapping
    @Transactional// samma transaktion för båda anropen
    public Map<String,Integer> like() {
        repo.increment();       // öka
        Integer total = repo.getTotal(); // hämta nya värdet
        return Map.of("total", total);
    } */
}


package com.example.travanalysserver.controller.userdata;

import com.example.travanalysserver.entity.Likes;
import com.example.travanalysserver.repository.LikesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikesController {

    private final LikesRepo repo;

    /** Hämta raden med id=1 eller skapa den (total=0) om den saknas */
    private Likes getOrCreate() {
        return repo.findById(1L)
                .orElseGet(() -> repo.save(new Likes(1L, 0)));
    }

    @GetMapping
    @Transactional
    public Map<String, Integer> get() {
        Likes row = getOrCreate();
        return Map.of("total", row.getTotal());
    }

    @PostMapping
    @Transactional
    public Map<String, Integer> like() {
        Likes row = getOrCreate();
        Integer current = row.getTotal() == null ? 0 : row.getTotal();
        row.setTotal(current + 1);   // JPA sparar vid commit (dirty checking)
        return Map.of("total", row.getTotal());
    }
}

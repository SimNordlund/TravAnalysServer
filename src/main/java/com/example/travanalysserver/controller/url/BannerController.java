package com.example.travanalysserver.controller.url;

import com.example.travanalysserver.entitysec.Banner;
import com.example.travanalysserver.repositorysec.BannerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/banner")
public class BannerController {

    private final BannerRepo bannerRepo;

    @GetMapping(produces = "application/json")
    @Transactional(readOnly = true, transactionManager = "secondaryTransactionManager")
    public List<Banner> getAll() {
        return bannerRepo.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Transactional(readOnly = true, transactionManager = "secondaryTransactionManager")
    public ResponseEntity<Banner> getById(@PathVariable Long id) {
        return bannerRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
package com.example.travanalysserver.controller;

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

    private final BannerRepo bannerRepo; //Changed!

    @GetMapping(produces = "application/json") //Changed!
    @Transactional(readOnly = true, transactionManager = "secondaryTransactionManager")
    public List<Banner> getAll() { //Changed!
        return bannerRepo.findAll(); //Changed!
    }

    @GetMapping(value = "/{id}", produces = "application/json") //Changed!
    @Transactional(readOnly = true, transactionManager = "secondaryTransactionManager")
    public ResponseEntity<Banner> getById(@PathVariable Long id) { //Changed!
        return bannerRepo.findById(id) //Changed!
                .map(ResponseEntity::ok) //Changed!
                .orElse(ResponseEntity.notFound().build()); //Changed!
    }
}

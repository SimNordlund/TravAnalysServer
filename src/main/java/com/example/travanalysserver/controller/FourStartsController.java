package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.starts.FourStartsDTO;
import com.example.travanalysserver.entity.Competition;
import com.example.travanalysserver.entity.FourStarts;
import com.example.travanalysserver.repository.FourStartsRepo;
import com.example.travanalysserver.service.interfaces.FourStartsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fourStarts")
public class FourStartsController {

    private final FourStartsService fourStartsService;
    private final FourStartsRepo fourStartsRepo;

    @GetMapping("/findData")
    public FourStartsDTO getFourStartsData (@RequestParam Long completeHorseId){
        return fourStartsService.findFourStartsSingleData(completeHorseId);
    }

    @PostMapping("/createFourStarts")
    public ResponseEntity<String> addFourStarts(@RequestBody FourStarts fourStarts) {
        fourStartsRepo.save(fourStarts);
        return new ResponseEntity<>("Data för fyra starter sparad", HttpStatus.CREATED);
    }
}

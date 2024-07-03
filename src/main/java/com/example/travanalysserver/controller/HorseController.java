package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.horse.HorseDTO;
import com.example.travanalysserver.service.interfaces.HorseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/horses")
//@PreAuthorize()
public class HorseController {

    private final HorseService horseService;

    @GetMapping("/{id}")
    public HorseDTO getHorseById(@PathVariable Long id) {
        return horseService.getHorseById(id);
    }
}

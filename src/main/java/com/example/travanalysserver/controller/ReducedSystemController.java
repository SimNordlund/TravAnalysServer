package com.example.travanalysserver.controller;

import com.example.travanalysserver.entity.ReducedSystem;
import com.example.travanalysserver.repository.ReducedSystemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ReducedSystemController {

    private final ReducedSystemRepo reducedSystemRepo;

    //Patch vs Put

    @GetMapping(value = "/s1", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getSystemOne(@RequestParam Long id) {
        return reducedSystemRepo.findById(id)
                .map(system -> ResponseEntity.ok(system.getRd())) // "rd" is presumably your string field
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/s2")
    public Optional<ReducedSystem> getSystemTwo(@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }

    @GetMapping("/s3")
    public Optional<ReducedSystem> getSystemThree(@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }

    @GetMapping("/s4")
    public Optional<ReducedSystem> getSystemFour(@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }

    @GetMapping("/s5")
    public Optional<ReducedSystem> getSystemFive(@RequestParam Long id) {
        return reducedSystemRepo.findById(id);
    }


 /*   @PostMapping("/save/system/one")
    public ResponseEntity<String> addReducedSystem(@RequestBody ReducedSystem reducedSystem) {
        reducedSystemRepo.save(reducedSystem);
        return new ResponseEntity<>("Reduced system added successfully", HttpStatus.CREATED);
    } */

    @PostMapping(value = "/save/system/one", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addReducedSystem(@RequestBody String rawXml) {
        ReducedSystem reducedSystem = new ReducedSystem();
        reducedSystem.setRd(rawXml); // Save the raw XML as a string in the database
        reducedSystemRepo.save(reducedSystem);

        return new ResponseEntity<>("Reduced system added successfully", HttpStatus.CREATED);
    }


    @PutMapping("/update/system/one")
    public ResponseEntity<String> putSystemOne(@RequestBody ReducedSystem reducedSystem) {

        if (reducedSystemRepo.findById(reducedSystem.getId()).isPresent()) {
            ReducedSystem temp = reducedSystemRepo.findById(reducedSystem.getId()).orElseThrow(); //Uppdatera denna sen
            temp.setRd(reducedSystem.getRd());
            reducedSystemRepo.save(temp);

            return new ResponseEntity<>("Reduced system updated successfully", HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Reduced sket sig", HttpStatus.FORBIDDEN);
    }
}
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

    @GetMapping(value = "/s1", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getSystemOne(@RequestParam Long id) {
        return reducedSystemRepo.findById(id)
                .map(system -> ResponseEntity.ok(system.getRd()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/save/system/one", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addReducedSystem(@RequestBody String rawXml) {
        ReducedSystem reducedSystem = new ReducedSystem();
        reducedSystem.setRd(rawXml);
        reducedSystemRepo.save(reducedSystem);

        return new ResponseEntity<>("Reduced system added successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/system/one", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> putSystemOne(@RequestParam Long id, @RequestBody String rawXml) {
        Optional<ReducedSystem> optionalReducedSystem = reducedSystemRepo.findById(id);

        if (optionalReducedSystem.isPresent()) {

            ReducedSystem reducedSystem = optionalReducedSystem.get();
            reducedSystem.setRd(rawXml);
            reducedSystemRepo.save(reducedSystem);

            return new ResponseEntity<>("Reduced system updated successfully", HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Reduced system not found", HttpStatus.NOT_FOUND);
    }


 /*   @PutMapping("/update/system/one")
    public ResponseEntity<String> putSystemOne(@RequestBody ReducedSystem reducedSystem) {

        if (reducedSystemRepo.findById(reducedSystem.getId()).isPresent()) {
            ReducedSystem temp = reducedSystemRepo.findById(reducedSystem.getId()).orElseThrow(); //Uppdatera denna sen
            temp.setRd(reducedSystem.getRd());
            reducedSystemRepo.save(temp);

            return new ResponseEntity<>("Reduced system updated successfully", HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Reduced sket sig", HttpStatus.FORBIDDEN);
    } */
}
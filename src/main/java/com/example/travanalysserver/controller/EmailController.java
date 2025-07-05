package com.example.travanalysserver.controller;

import com.example.travanalysserver.entity.EmailAdress;
import com.example.travanalysserver.repository.EmailAdressRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
public class EmailController {

     private final EmailAdressRepo emailAdressRepo;

    @PostMapping("/storeInfo")
    public ResponseEntity<Void> storeEmailOrPhone(@RequestBody @Valid EmailAdress emailAdress) {
        if (emailAdress.getEmail() == null && emailAdress.getPhone() == null) {
            return ResponseEntity.badRequest().build();
        }
        emailAdressRepo.save(emailAdress);
        return ResponseEntity.ok().build();
    }

    //TODO
    //SKAPA DTO OCKSÅ
    //SERVICELAGER
}

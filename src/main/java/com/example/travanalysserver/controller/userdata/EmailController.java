package com.example.travanalysserver.controller.userdata;

import com.example.travanalysserver.entity.EmailAdress;
import com.example.travanalysserver.repository.EmailAdressRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

        if ((emailAdress.getEmail() != null && emailAdressRepo.existsByEmail(emailAdress.getEmail()))
                || (emailAdress.getPhone() != null && emailAdressRepo.existsByPhone(emailAdress.getPhone()))) {
            return ResponseEntity.ok().build();
        }

        try {
            emailAdressRepo.save(emailAdress);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException ex) {
            // Skydda mot race condition: behandla som lyckat
            return ResponseEntity.ok().build();
        }
    }

    //TODO
    //SKAPA DTO OCKSÅ
    //SERVICELAGER
}

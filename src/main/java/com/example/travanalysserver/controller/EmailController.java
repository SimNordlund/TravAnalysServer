package com.example.travanalysserver.controller;

import com.example.travanalysserver.repository.EmailAdressRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emailToBeStored")
public class EmailController {

     private final EmailAdressRepo emailAdressRepo;

 /*   @PostMapping("/storeEmail")
    public ResponseEntity<String> storeEmail(@RequestBody UserEmail userEmail) {
        //spara repo
        //returnera responsentity, se completehorsecontroller
    } */

    //TODO
    //SKAPA DTO OCKSÅ
    //SERVICELAGER
}

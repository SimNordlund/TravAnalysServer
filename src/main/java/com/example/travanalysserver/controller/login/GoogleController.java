package com.example.travanalysserver.controller.login;

import com.example.travanalysserver.entity.login.GoogleUser;
import com.example.travanalysserver.repository.GoogleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("g-login")

public class GoogleController {

    private final GoogleRepo googleRepo;

 /*   @GetMapping("/email1337")
    public ResponseEntity<String> getEmail1337(@RequestParam String email) {
        GoogleUser googleUser = googleRepo.findByEmail(email);

        if (googleUser == null) {
            return ResponseEntity.status(404).body("User with email " + email + " not found");
        }

        String userEmail = googleUser.getEmail();
        return ResponseEntity.ok(userEmail);
    } */

    @GetMapping("/email")
    public ResponseEntity<String> getEmail(@org.springframework.security.core.annotation.AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }

        String email = (String) principal.getAttributes().get("email");
        if (email == null) {
            return ResponseEntity.status(404).body("Email not found");
        }

        return ResponseEntity.ok(email);
    }


}

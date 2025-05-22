package com.example.travanalysserver.controller;

import com.example.travanalysserver.entity.login.User;
import com.example.travanalysserver.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
//@PreAuthorize("hasAuthority('Admin')")
public class UserController {

    private final UserRepo userRepo;

    static class AuthRequest {
        public String username;
        public String password;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticateUser(@RequestBody AuthRequest request) {
        User user = userRepo.findByUsernameAndPassword(request.username, request.password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body(null);

        }
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userRepo.findByUsername(username);
    }
    @GetMapping("/{password}")
    public User getUserByPassword(@PathVariable String password) {
        return userRepo.findByPassword(password);
    }
}

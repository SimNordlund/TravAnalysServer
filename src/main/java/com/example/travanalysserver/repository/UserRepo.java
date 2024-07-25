package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.login.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository <User, UUID> {
    User findByUsername(String username);
    User findByPassword(String password);
    User findByUsernameAndPassword(String username, String password);
}

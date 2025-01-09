package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.login.GoogleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoogleRepo extends JpaRepository <GoogleUser, Long> {
    boolean existsByEmail(String email);
    GoogleUser findByEmail(String email);
}

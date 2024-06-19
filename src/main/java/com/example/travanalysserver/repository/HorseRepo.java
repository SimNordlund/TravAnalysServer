package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Horse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorseRepo extends JpaRepository<Horse, Long> {
}

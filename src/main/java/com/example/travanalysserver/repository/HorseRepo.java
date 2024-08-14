package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.testing.Horse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HorseRepo extends JpaRepository<Horse, Long> {
}

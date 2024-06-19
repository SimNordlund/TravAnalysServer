package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepo extends JpaRepository <Performance, Long> {
}

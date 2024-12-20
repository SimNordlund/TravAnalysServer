package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.ReducedSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReducedSystemRepo extends  JpaRepository<ReducedSystem, Long> {
}


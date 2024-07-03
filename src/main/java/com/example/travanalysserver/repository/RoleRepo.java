package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepo extends JpaRepository <Role, UUID> {
    Role findByName(String name);
}

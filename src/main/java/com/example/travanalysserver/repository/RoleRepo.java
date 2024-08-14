package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.login.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepo extends JpaRepository <Role, UUID> {
    Role findByName(String name);
}

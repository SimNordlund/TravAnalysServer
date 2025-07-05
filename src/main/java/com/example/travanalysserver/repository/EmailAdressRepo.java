package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.EmailAdress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailAdressRepo extends JpaRepository <EmailAdress, Long> {
}

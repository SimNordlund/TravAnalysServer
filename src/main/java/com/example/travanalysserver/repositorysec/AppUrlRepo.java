package com.example.travanalysserver.repositorysec;

import com.example.travanalysserver.entitysec.AppUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUrlRepo extends JpaRepository <AppUrl, Long> {
}

package com.example.travanalysserver.repositorysec;

import com.example.travanalysserver.entitysec.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepo extends JpaRepository <Banner, Long> {
}

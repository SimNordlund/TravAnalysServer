package com.example.travanalysserver.repositorysec;

import com.example.travanalysserver.entitysec.Roi;
import com.example.travanalysserver.entitysec.RoiView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoiRepo extends JpaRepository<Roi, Long> {
    List<RoiView> findAllProjectedBy();
}

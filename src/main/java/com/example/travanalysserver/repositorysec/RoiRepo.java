package com.example.travanalysserver.repositorysec;

import com.example.travanalysserver.entitysec.Roi;
import com.example.travanalysserver.entitysec.RoiView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
@Repository
public interface RoiRepo extends JpaRepository<Roi, Long> {
    List<RoiView> findAllProjectedBy();
    List<RoiView> findAllByUpdatedAtAfter(LocalDateTime cutoff);
    List<RoiView> findAllByRankIdIn(Collection<Long> rankIds);
}

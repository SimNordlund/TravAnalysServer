package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.schedule.SyncMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SyncMetaRepo extends JpaRepository<SyncMeta,String> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE syncmeta", nativeQuery = true)
    void truncateAll();

}

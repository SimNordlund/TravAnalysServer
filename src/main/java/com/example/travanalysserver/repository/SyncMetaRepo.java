package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.schedule.SyncMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Behövs denna egentligen?
public interface SyncMetaRepo extends JpaRepository<SyncMeta,String> {
}

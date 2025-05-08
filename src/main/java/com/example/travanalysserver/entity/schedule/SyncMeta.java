package com.example.travanalysserver.entity.schedule;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SyncMeta {
    @Id
    private String name;
    private LocalDateTime lastRun;
}

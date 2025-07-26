package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LikesRepo extends JpaRepository<Likes, Long> {

    @Modifying
    @Transactional         // behövs för update-queryn
    @Query(value = """
            UPDATE likes
            SET total = total + 1
            WHERE id = 1
            """, nativeQuery = true)
    void increment();

    @Query(value = """
            SELECT total
            FROM likes
            WHERE id = 1
            """, nativeQuery = true)
    Integer getTotal();
}

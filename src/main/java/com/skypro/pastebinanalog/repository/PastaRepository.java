package com.skypro.pastebinanalog.repository;

import com.skypro.pastebinanalog.enums.Status;
import com.skypro.pastebinanalog.model.Pasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PastaRepository extends JpaRepository<Pasta, Long> {

    @Modifying
    @Query(value="DELETE FROM Pasta p WHERE p.expiredDate < now()")
    void deleteAllByExpiredDateIsBefore(Instant now);

    Optional<Pasta> findPastaByHashAndExpiredDateIsAfter(String hash, Instant date);

    List<Pasta> findTop10ByStatusAndExpiredDateAfterOrderByPublishedDateDesc(Status status, Instant date);

    List<Pasta> findAllByTitleContainsOrBodyContains(String title, String body);


}

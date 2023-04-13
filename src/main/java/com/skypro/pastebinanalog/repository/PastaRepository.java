package com.skypro.pastebinanalog.repository;

import com.skypro.pastebinanalog.model.Pasta;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PastaRepository extends JpaRepository<Pasta, Long> {

    Optional<Pasta> findPastaByHash(String hash);

    void deleteAllByExpiredDateIsBefore(Instant now);

    @Query(value = "SELECT p FROM pasta p WHERE p.status = 'PUBLIC' ORDER BY p.published_date DESC LIMIT 10",
            nativeQuery = true)
    List<Pasta> findTenLastPasta();

    List<Pasta> findAllBy(Specification<Pasta> spec);
}

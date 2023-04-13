package com.skypro.pastebinanalog.repository;

import com.skypro.pastebinanalog.model.Pasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PastaRepository extends JpaRepository<Pasta, Long> {




}

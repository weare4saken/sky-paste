package com.skypro.pastebinanalog.service;

import com.skypro.pastebinanalog.dto.PastaCreateDTO;
import com.skypro.pastebinanalog.dto.PastaUrlDTO;
import com.skypro.pastebinanalog.model.Pasta;
import com.skypro.pastebinanalog.repository.PastaRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PastaService {

    private final PastaRepository pastaRepository;

    public PastaService(PastaRepository pastaRepository) {
        this.pastaRepository = pastaRepository;
    }


    public PastaUrlDTO createPasta(PastaCreateDTO pastaCreateDTO) {
        Pasta pasta = pastaCreateDTO.to();
        pasta.setExpiredDate(Instant.now().plus(pastaCreateDTO.getExpirationTime()));
        return null;
    }
}

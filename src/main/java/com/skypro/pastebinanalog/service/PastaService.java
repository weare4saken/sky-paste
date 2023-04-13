package com.skypro.pastebinanalog.service;

import com.skypro.pastebinanalog.dto.PastaCreateDTO;
import com.skypro.pastebinanalog.dto.PastaDTO;
import com.skypro.pastebinanalog.dto.PastaUrlDTO;
import com.skypro.pastebinanalog.enums.ExpirationTime;
import com.skypro.pastebinanalog.exception.PastaNotFoundException;
import com.skypro.pastebinanalog.model.Pasta;
import com.skypro.pastebinanalog.repository.PastaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PastaService {

    private final PastaRepository pastaRepository;

    public PastaService(PastaRepository pastaRepository) {
        this.pastaRepository = pastaRepository;
    }


    public PastaUrlDTO createPasta(PastaCreateDTO pastaCreateDTO) {
        Pasta pasta = pastaCreateDTO.to();
        pasta.setPublishedDate(Instant.now());

        if (pastaCreateDTO.getExpirationTime() == ExpirationTime.UNLIMITED) {
            pasta.setExpiredDate(null);
        } else {
            pasta.setExpiredDate(Instant.now().plus(pastaCreateDTO.getExpirationTime().getTime(),
                                                    pastaCreateDTO.getExpirationTime().getUnit()));
        }

        pasta.setHash(UUID.randomUUID().toString().substring(0, 7));
        pastaRepository.save(pasta);
        return PastaUrlDTO.from(pasta);
    }

    public PastaDTO getPastaByHash(String hash) {
        Pasta pasta = pastaRepository.findPastaByHash(hash).orElseThrow(PastaNotFoundException::new);
        return PastaDTO.from(pasta);
    }

    public List<PastaDTO> getPublicPastaList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        return pastaRepository.findTenLastPasta()
                .stream()
                .map(PastaDTO::from)
                .collect(Collectors.toList());
    }

}

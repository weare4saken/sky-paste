package com.skypro.pastebinanalog.service;

import com.skypro.pastebinanalog.cryptograph.RandomHashGenerator;
import com.skypro.pastebinanalog.dto.PastaCreateDTO;
import com.skypro.pastebinanalog.dto.PastaDTO;
import com.skypro.pastebinanalog.dto.PastaUrlDTO;
import com.skypro.pastebinanalog.enums.Status;
import com.skypro.pastebinanalog.exception.PastaNotFoundException;
import com.skypro.pastebinanalog.model.Pasta;
import com.skypro.pastebinanalog.repository.PastaRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
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
        pasta.setExpiredDate(pasta.getPublishedDate()
                                                    .plus(pastaCreateDTO.getExpirationTime().getTime(),
                                                          pastaCreateDTO.getExpirationTime().getUnit()));
        pasta.setHash(RandomHashGenerator.generateHash());

        pastaRepository.save(pasta);
        return PastaUrlDTO.from(pasta);
    }

    public PastaDTO getPastaByHash(String hash) {
        Pasta pasta = pastaRepository.findPastaByHashAndExpiredDateIsAfter(hash, Instant.now())
                                     .orElseThrow(PastaNotFoundException::new);

        return PastaDTO.from(pasta);
    }

    public List<PastaDTO> getPublicPastaList() {
        return pastaRepository.findTop10ByStatusAndExpiredDateAfterOrderByPublishedDateDesc(Status.PUBLIC, Instant.now())
                .stream()
                .map(PastaDTO::from)
                .collect(Collectors.toList());
    }

    public List<PastaDTO> searchBy(String title, String body) {
        return pastaRepository.findAllByTitleContainsOrBodyContains(Status.PUBLIC, title, body)
                .stream()
                .map(PastaDTO::from)
                .collect(Collectors.toList());
    }

}

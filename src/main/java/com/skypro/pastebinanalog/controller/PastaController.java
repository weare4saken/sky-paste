package com.skypro.pastebinanalog.controller;

import com.skypro.pastebinanalog.dto.PastaCreateDTO;
import com.skypro.pastebinanalog.dto.PastaUrlDTO;
import com.skypro.pastebinanalog.enums.ExpirationTime;
import com.skypro.pastebinanalog.enums.Status;
import com.skypro.pastebinanalog.service.PastaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PastaController {

    private final PastaService pastaService;

    public PastaController(PastaService pasteFieldService) {
        this.pastaService = pasteFieldService;
    }

    @GetMapping
    public List<PastaDTO> getPublicList() {
        return pastaService.getPublicPastaList();
    }

    @GetMapping("/{hash}")
    public PastaDTO getByHash(@PathVariable String hash) {
        return pastaService.getPastaByHash();
    }

    @PostMapping
    public PastaUrlDTO addPasta(@RequestParam(name = "status") Status status,
                                @RequestParam(name = "when to delete") ExpirationTime expirationTime,
                                @RequestBody PastaCreateDTO pastaCreateDTODTO) {
        return pastaService.createPasta(pastaCreateDTODTO);
    }

}

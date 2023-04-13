package com.skypro.pastebinanalog.controller;

import com.skypro.pastebinanalog.dto.PastaCreateDTO;
import com.skypro.pastebinanalog.dto.PastaDTO;
import com.skypro.pastebinanalog.dto.PastaUrlDTO;
import com.skypro.pastebinanalog.enums.ExpirationTime;
import com.skypro.pastebinanalog.enums.Status;
import com.skypro.pastebinanalog.service.PastaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my-awesome-pastebin.tld")
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
    public ResponseEntity<PastaDTO> getByHash(@PathVariable String hash) {
        return ResponseEntity.ok(pastaService.getPastaByHash(hash));
    }

    @PostMapping
    public PastaUrlDTO addPasta(@RequestParam(name = "status") Status status,
                                @RequestParam(name = "when to delete") ExpirationTime expirationTime,
                                @RequestBody PastaCreateDTO pastaCreateDTO) {
        return pastaService.createPasta(pastaCreateDTO, status, expirationTime);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PastaDTO>> searchBy(@RequestParam(required = false) String title,
                                                    @RequestParam(required = false) String body){
        return ResponseEntity.ok(pastaService.search(title, body));
    }

}

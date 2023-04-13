package com.skypro.pastebinanalog.dto;

import com.skypro.pastebinanalog.model.Pasta;
import lombok.Data;

import java.time.Instant;

@Data
public class PastaDTO {

    private String title;
    private String body;

    public static PastaDTO from(Pasta pasta) {
        PastaDTO dto = new PastaDTO();
        dto.setTitle(pasta.getTitle());
        dto.setBody(pasta.getBody());
        return dto;
    }

}


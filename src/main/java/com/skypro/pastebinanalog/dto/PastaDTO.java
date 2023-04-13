package com.skypro.pastebinanalog.dto;

import com.skypro.pastebinanalog.model.Pasta;
import lombok.Data;

import java.time.Instant;

@Data
public class PastaDTO {

    private String title;
    private String body;
    private Instant publishedDate;

    public static PastaDTO from(Pasta pasta) {
        PastaDTO dto = new PastaDTO();
        dto.setTitle(pasta.getTitle());
        dto.setBody(pasta.getBody());
        dto.setPublishedDate(pasta.getPublishedDate());
        return dto;
    }

}


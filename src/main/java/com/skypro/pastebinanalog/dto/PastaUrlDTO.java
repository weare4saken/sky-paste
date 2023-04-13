package com.skypro.pastebinanalog.dto;

import com.skypro.pastebinanalog.model.Pasta;
import lombok.*;


@Data
public class PastaUrlDTO {

    private String url;

    public static PastaUrlDTO from(Pasta pasta) {
        PastaUrlDTO url = new PastaUrlDTO();
        url.setUrl("my-awesome-pastebin.tld/" + pasta.getHash());
        return url;
    }

}

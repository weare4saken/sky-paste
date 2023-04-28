package com.skypro.pastebinanalog.dto;

import com.skypro.pastebinanalog.model.Pasta;
import lombok.*;


@Data
public class PastaUrlDTO {

    private static String baseUrl = "my-awesome-pastebin.tld/";
    private String url;

    public static PastaUrlDTO from(Pasta pasta) {
        PastaUrlDTO url = new PastaUrlDTO();
        url.setUrl(baseUrl + pasta.getHash());
        return url;
    }

}

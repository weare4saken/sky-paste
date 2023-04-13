package com.skypro.pastebinanalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skypro.pastebinanalog.enums.ExpirationTime;
import com.skypro.pastebinanalog.enums.Status;
import com.skypro.pastebinanalog.model.Pasta;
import lombok.Data;

@Data
public class PastaCreateDTO {

    private String title;
    private String body;

    public Pasta to() {
        Pasta pasta = new Pasta();
        pasta.setTitle(this.getTitle());
        pasta.setBody(this.getBody());
        return pasta;
    }

}

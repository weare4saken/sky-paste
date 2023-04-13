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
    @JsonIgnore
    private ExpirationTime expirationTime;
    @JsonIgnore
    private Status status;

    public Pasta to() {
        Pasta pasta = new Pasta();
        pasta.setTitle(this.getTitle());
        pasta.setBody(this.getBody());
        pasta.setStatus(this.getStatus());
        return pasta;
    }

}

package com.skypro.pastebinanalog.model;

import com.skypro.pastebinanalog.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
@Getter
@Setter
public class Pasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;
    private String hash;
    private Instant expiredDate;
    @Enumerated(EnumType.STRING)
    private Status status;

}

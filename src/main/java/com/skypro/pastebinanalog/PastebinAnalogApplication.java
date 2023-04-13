package com.skypro.pastebinanalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PastebinAnalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PastebinAnalogApplication.class, args);
    }

}

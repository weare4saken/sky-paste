package com.skypro.pastebinanalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.pastebinanalog.dto.PastaCreateDTO;
import com.skypro.pastebinanalog.dto.PastaDTO;
import com.skypro.pastebinanalog.dto.PastaUrlDTO;
import com.skypro.pastebinanalog.enums.ExpirationTime;
import com.skypro.pastebinanalog.enums.Status;
import com.skypro.pastebinanalog.model.Pasta;
import com.skypro.pastebinanalog.repository.PastaRepository;
import com.skypro.pastebinanalog.service.PastaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PastaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PastaService pastaService;
    @Autowired
    private PastaRepository pastaRepository;
    private ObjectMapper objectMapper;
    private final String baseUrl = "/my-awesome-pastebin.tld";
    private final Pasta pasta = new Pasta();

    private static final String PASTA_HASH = "1a2b3c4d";
    private static final String PASTA_TITLE = "Title";
    private static final String PASTA_BODY = "Body";
    private static final Status PASTA_STATUS = Status.PUBLIC;
    private static final ExpirationTime PASTA_EXPIRATION = ExpirationTime.THREE_HOURS;


    @BeforeEach
    public void setUp() {
        Pasta pasta = new Pasta();
        pasta.setTitle(PASTA_TITLE);
        pasta.setBody(PASTA_BODY);
        pasta.setStatus(PASTA_STATUS);
        pasta.setHash(PASTA_HASH);
        pastaRepository.save(pasta);
    }

    @AfterEach
    public void cleanUp() {
        pastaRepository.delete(pasta);
    }


    @Test
    public void testGetByHash() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/" + PASTA_HASH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(PASTA_TITLE))
                .andExpect(jsonPath("$.body").value(PASTA_BODY));
    }

    /*@Test
    public void testAddPasta() throws Exception {
        PastaCreateDTO pastaCreateDTO = new PastaCreateDTO();
        pastaCreateDTO.setTitle(PASTA_TITLE);
        pastaCreateDTO.setBody(PASTA_BODY);

        MvcResult result = mockMvc.perform(post(baseUrl)
                        .param("status", PASTA_STATUS.toString())
                        .param("when to delete", PASTA_EXPIRATION.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"" + PASTA_TITLE + "\",\"body\":\"" + PASTA_BODY + "\"}"))
                .andExpect(status().isOk()).andReturn();

        //удаление с базы
    }

    @Test
    public void testGetPublicList() throws Exception {
        PastaCreateDTO pasta1 = new PastaCreateDTO();
        pasta1.setTitle("Test1");
        pasta1.setBody("Body1");
        pastaService.createPasta(pasta1, PASTA_STATUS, PASTA_EXPIRATION);

        PastaCreateDTO pasta2 = new PastaCreateDTO();
        pasta2.setTitle("Test2");
        pasta2.setBody("Body2");
        pastaService.createPasta(pasta2, Status.UNLISTED, PASTA_EXPIRATION);


        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length").value(1))
                .andExpect(jsonPath("$[0].title").value(pasta1.getTitle()))
                .andExpect(jsonPath("$[0].body").value(pasta1.getBody()));
    }*/

    @Test
    public void testSearchBy() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/search")
                        .param("title", PASTA_TITLE).param("body", PASTA_BODY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value(PASTA_TITLE))
                .andExpect(jsonPath("$[0].body").value(PASTA_BODY));

    }

}

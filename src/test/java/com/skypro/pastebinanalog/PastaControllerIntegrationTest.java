package com.skypro.pastebinanalog;

import com.skypro.pastebinanalog.enums.ExpirationTime;
import com.skypro.pastebinanalog.enums.Status;
import com.skypro.pastebinanalog.model.Pasta;
import com.skypro.pastebinanalog.repository.PastaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PastaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PastaRepository pastaRepository;
    private final String baseUrl = "/my-awesome-pastebin.tld";
    private final Pasta pasta = new Pasta();

    private static final String PASTA_HASH = "1a2b3c4d";
    private static final String PASTA_TITLE = "Title";
    private static final String PASTA_BODY = "Body";
    private static final Status PASTA_STATUS = Status.PUBLIC;
    private static final Instant PUBLISHED_DATE = Instant.now();
    private static final ExpirationTime PASTA_EXPIRATION = ExpirationTime.THREE_HOURS;


    @BeforeEach
    public void setUp() {
        pasta.setTitle(PASTA_TITLE);
        pasta.setBody(PASTA_BODY);
        pasta.setStatus(PASTA_STATUS);
        pasta.setHash(PASTA_HASH);pasta.setPublishedDate(PUBLISHED_DATE);
        pasta.setExpiredDate(pasta.getPublishedDate()
                .plus(PASTA_EXPIRATION.getTime(),PASTA_EXPIRATION.getUnit()));
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

    @Test
    public void testGetPublicListReturnsCorrectList() throws Exception {
        Pasta pasta2 = new Pasta();
        pasta2.setTitle(PASTA_TITLE);
        pasta2.setBody(PASTA_BODY);
        pasta2.setStatus(PASTA_STATUS);
        pasta2.setHash("1d2c3b4a");
        pasta2.setPublishedDate(PUBLISHED_DATE);
        pasta2.setExpiredDate(pasta2.getPublishedDate()
                .plus(PASTA_EXPIRATION.getTime(),PASTA_EXPIRATION.getUnit()));
        pastaRepository.save(pasta2);

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value(PASTA_TITLE))
                .andExpect(jsonPath("$[0].body").value(PASTA_BODY))
                .andExpect(jsonPath("$[1].title").value(PASTA_TITLE))
                .andExpect(jsonPath("$[1].body").value(PASTA_BODY));

        pastaRepository.delete(pasta2);
    }

    @Test
    public void testSearchByReturnsCorrectList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/search")
                        .param("title", PASTA_TITLE).param("body", PASTA_BODY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value(PASTA_TITLE))
                .andExpect(jsonPath("$[0].body").value(PASTA_BODY));
    }

}

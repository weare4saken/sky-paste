package com.skypro.pastebinanalog;

import com.skypro.pastebinanalog.cryptograph.RandomHashGenerator;
import com.skypro.pastebinanalog.dto.PastaCreateDTO;
import com.skypro.pastebinanalog.dto.PastaDTO;
import com.skypro.pastebinanalog.dto.PastaUrlDTO;
import com.skypro.pastebinanalog.enums.ExpirationTime;
import com.skypro.pastebinanalog.enums.Status;
import com.skypro.pastebinanalog.exception.PastaNotFoundException;
import com.skypro.pastebinanalog.model.Pasta;
import com.skypro.pastebinanalog.repository.PastaRepository;
import com.skypro.pastebinanalog.service.PastaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PastaServiceTest {

    @Mock
    private PastaRepository repositoryMock;
    @InjectMocks
    private PastaService pastaService;


    @Test
    public void testCreatePastaShouldReturnNotNullUrl() {
        Pasta pasta = new Pasta();

        PastaCreateDTO createDTO = new PastaCreateDTO();
        createDTO.setExpirationTime(ExpirationTime.UNLIMITED);

        when(repositoryMock.save(any(Pasta.class))).thenReturn(pasta);

        PastaUrlDTO url = pastaService.createPasta(createDTO);

        assertNotNull(url);
    }

    @Test
    public void testGetPastaByHashShouldReturnCorrectPasta() {
        Pasta pasta = new Pasta();
        String hash = RandomHashGenerator.generateHash();
        String title = "Title";
        String body = "Body";
        pasta.setTitle(title);
        pasta.setBody(body);
        pasta.setHash(hash);

        when(repositoryMock.findPastaByHashAndExpiredDateIsAfter(eq(hash),
                ArgumentMatchers.any(Instant.class))).thenReturn(Optional.of(pasta));

        PastaDTO pastaDTO = pastaService.getPastaByHash(hash);

        assertEquals(title, pastaDTO.getTitle());
        assertEquals(body, pastaDTO.getBody());
    }


    @Test
    public void testGetPastaByHashThrowsException() {
        String hash = RandomHashGenerator.generateHash();

        when(repositoryMock.findPastaByHashAndExpiredDateIsAfter(eq(hash),
                ArgumentMatchers.any(Instant.class))).thenReturn(Optional.empty());

        assertThrows(PastaNotFoundException.class, () -> pastaService.getPastaByHash(hash));
    }


    @Test
    public void testGetPublicListShouldReturnCorrectList() {
        List<Pasta> pastaList = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            Pasta pasta = new Pasta();
            if(i < 5){
                pasta.setStatus(Status.PUBLIC);
            } else {
                pasta.setStatus(Status.UNLISTED);
            }
            pastaList.add(pasta);
        }

        when(repositoryMock.findTop10ByStatusAndExpiredDateAfterOrderByPublishedDateDesc(eq(Status.PUBLIC), any(Instant.class)))
                .thenReturn(pastaList.subList(0, 5));

        List<PastaDTO> pastaDTOList = pastaService.getPublicPastaList();

        assertEquals(5, pastaDTOList.size());
    }

    @Test
    public void testSearchShouldReturnCorrectList() {
        String title = "Title";
        String body = "";

        Pasta pasta1 = new Pasta();
        pasta1.setTitle("Title1");
        pasta1.setBody("Body1");
        pasta1.setStatus(Status.PUBLIC);
        pasta1.setHash(RandomHashGenerator.generateHash());
        pasta1.setPublishedDate(Instant.now());
        pasta1.setExpiredDate(Instant.MAX);

        Pasta pasta2 = new Pasta();
        pasta2.setTitle("Title2");
        pasta2.setBody("Body2");
        pasta2.setStatus(Status.PUBLIC);
        pasta2.setHash(RandomHashGenerator.generateHash());
        pasta2.setPublishedDate(Instant.now());
        pasta2.setExpiredDate(Instant.MAX);

        List<Pasta> pastaList = new ArrayList<>();
        pastaList.add(pasta1);
        pastaList.add(pasta2);

        when(repositoryMock.findAllByTitleContainsOrBodyContains(Status.PUBLIC, title, body)).thenReturn(pastaList);

        List<PastaDTO> pastaDTOList = pastaService.searchBy(title, body);

        assertEquals(pastaList.size(), pastaDTOList.size());
        assertEquals(pasta1.getTitle(), pastaDTOList.get(0).getTitle());
        assertEquals(pasta1.getBody(), pastaDTOList.get(0).getBody());
        assertEquals(pasta2.getTitle(), pastaDTOList.get(1).getTitle());
        assertEquals(pasta2.getBody(), pastaDTOList.get(1).getBody());
    }

}

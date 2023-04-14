package com.skypro.pastebinanalog;

import com.skypro.pastebinanalog.dto.PastaCreateDTO;
import com.skypro.pastebinanalog.dto.PastaDTO;
import com.skypro.pastebinanalog.enums.ExpirationTime;
import com.skypro.pastebinanalog.enums.Status;
import com.skypro.pastebinanalog.exception.PastaNotFoundException;
import com.skypro.pastebinanalog.model.Pasta;
import com.skypro.pastebinanalog.repository.PastaRepository;
import com.skypro.pastebinanalog.service.PastaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

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

    private final Pasta pasta = new Pasta();


    @Test
    public void testCreatePasta() {
        PastaCreateDTO createDTO = new PastaCreateDTO();
        when(repositoryMock.save(any(Pasta.class))).thenReturn(pasta);

        pastaService.createPasta(createDTO, Status.UNLISTED, ExpirationTime.UNLIMITED);

        verify(repositoryMock, only()).save(any(Pasta.class));
    }

    @Test
    public void testGetPastaByHashShouldReturnCorrectPasta() {
        String hash = "1a2b3c";
        String title = "Test";
        pasta.setTitle(title);
        pasta.setHash(hash);

        when(repositoryMock.findPastaByHash(hash)).thenReturn(Optional.of(pasta));

        PastaDTO pastaDTO = pastaService.getPastaByHash(hash);

        assertEquals(title, pastaDTO.getTitle());
    }

    @Test
    public void testGetPastaByHashThrowsException() {
        String hash = "1a2b3c4d";

        when(repositoryMock.findPastaByHash(hash)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(PastaNotFoundException.class,
                () -> pastaService.getPastaByHash(hash));

        assertNull(exception.getMessage());

        verify(repositoryMock, only()).findPastaByHash(hash);
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

        when(repositoryMock.findTenLastPasta()).thenReturn(pastaList.subList(0, 5));

        List<PastaDTO> pastaDTOList = pastaService.getPublicPastaList();

        assertEquals(5, pastaDTOList.size());
    }

    @Test
    public void testSearch() {
        String title = "Title";
        String body = null;

        Pasta pasta1 = new Pasta();
        pasta1.setTitle("Title1");
        pasta1.setBody("Body1");
        Pasta pasta2 = new Pasta();
        pasta2.setTitle("Title2");
        pasta2.setBody("Body2");

        List<Pasta> pastaList = new ArrayList<>();
        pastaList.add(pasta1);
        pastaList.add(pasta2);

        when(repositoryMock.findAll(any(Specification.class))).thenReturn(pastaList);

        List<PastaDTO> pastaDTOList = pastaService.search(title, body);

        assertEquals(pastaList.size(), pastaDTOList.size());
        assertEquals(pasta1.getTitle(), pastaDTOList.get(0).getTitle());
        assertEquals(pasta1.getBody(), pastaDTOList.get(0).getBody());
        assertEquals(pasta2.getTitle(), pastaDTOList.get(1).getTitle());
        assertEquals(pasta2.getBody(), pastaDTOList.get(1).getBody());
    }

}

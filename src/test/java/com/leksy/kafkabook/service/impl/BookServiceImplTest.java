package com.leksy.kafkabook.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leksy.kafkabook.dto.RequestDTO;
import com.leksy.kafkabook.dto.request.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.*;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void toRequestDTOGet() throws IOException {
        String json = getJSON("src/test/resources/GetRequest");
        RequestDTO requestDTO = new ObjectMapper()
                .readerFor(RequestDTO.class)
                .readValue(json);
        Mockito.when(restTemplate.getForEntity(eq("http://localhost:8080/booking?id=2"), eq(BookDTO.class))).
                thenReturn(new ResponseEntity<>(requestDTO.getBody(), HttpStatus.OK));
        BookDTO bookDTO = bookService.createRequest(requestDTO).getBody();
        System.out.println(requestDTO.getParameters());
        Assertions.assertEquals(bookDTO, requestDTO.getBody());
    }

    @Test
    void toRequestDTOPost() throws IOException {
        String json = getJSON("src/test/resources/PostRequest.JSON");
        RequestDTO requestDTO = new ObjectMapper()
                .readerFor(RequestDTO.class)
                .readValue(json);
        HttpEntity<BookDTO> entity = new HttpEntity<>(requestDTO.getBody());
        Mockito.when(restTemplate.postForEntity(eq("http://localhost:8080/booking"), eq(entity), eq(BookDTO.class))).
                thenReturn(new ResponseEntity<>(requestDTO.getBody(), HttpStatus.OK));

        BookDTO bookDTO = bookService.createRequest(requestDTO).getBody();
        System.out.println(bookDTO);
        Assertions.assertEquals(bookDTO, requestDTO.getBody());
    }

    @Test
    void toRequestDTOPut() throws IOException {
        String json = getJSON("src/test/resources/PutRequest");
        RequestDTO requestDTO = new ObjectMapper()
                .readerFor(RequestDTO.class)
                .readValue(json);
        HttpEntity<BookDTO> entity = new HttpEntity<>(requestDTO.getBody());
        Mockito.when(restTemplate.exchange(eq("http://localhost:8080/booking?id=4"), eq(HttpMethod.PUT), eq(entity), eq(BookDTO.class))).
                thenReturn(new ResponseEntity<>(requestDTO.getBody(), HttpStatus.OK));

        BookDTO bookDTO = bookService.createRequest(requestDTO).getBody();
        System.out.println(bookDTO);
        Assertions.assertEquals(bookDTO, requestDTO.getBody());
    }

    private String getJSON(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        return IOUtils.toString(fis, "UTF-8");
    }

}
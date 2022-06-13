package com.leksy.kafkabook.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leksy.kafkabook.dto.RequestDTO;
import com.leksy.kafkabook.dto.request.BookDTO;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void toRequestDTOGet() throws JsonProcessingException {
        String json = """
                {
                  "http": "GET",
                  "URL": "http://localhost:8080/booking",
                   "header": {
                    "Accept": "application/json",
                    "Cache-Control": "no-cache"
                  },
                  "parameters": {
                    "id":2
                  }
                }""";
        RequestDTO requestDTO = new ObjectMapper()
                .readerFor(RequestDTO.class)
                .readValue(json);
        Mockito.when(restTemplate.getForEntity(eq("http://localhost:8080/booking?id=2"), eq(BookDTO.class))).
                thenReturn(new ResponseEntity<>(requestDTO.getBody(), HttpStatus.OK));
        BookDTO bookDTO = bookService.createRequest(requestDTO).getBody();
        System.out.println(bookDTO);
        Assertions.assertEquals(bookDTO, requestDTO.getBody());
    }

    @Test
    void toRequestDTOPost() throws JsonProcessingException {
        String json = """
                {
                  "http": "POST",
                  "URL": "http://localhost:8080/booking",
                  "body": {
                    "title": "title",
                    "authorName": "authorName",
                    "pageCount": 10,
                    "publishedDate": "2022-06-10",
                    "publication": "publication"
                  },
                  "header": {
                    "Accept": "application/json",
                    "Content-Type": "application/json;charset=iso-8859-1"
                  }
                }""";
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
    void toRequestDTOPut() throws JsonProcessingException {
        String json = """
                {
                  "http": "PUT",
                  "URL": "http://localhost:8080/booking",
                  "body": {
                    "title": "title1231",
                    "authorName": "authorName123",
                    "pageCount": 10000,
                    "publishedDate": "2022-06-10",
                    "publication": "publication3123"
                  },
                  "header": {
                    "Accept": "application/json",
                    "Content-Type": "application/json;charset=iso-8859-1"
                  },
                  "parameters": {
                    "id":4
                  }
                }""";
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


}
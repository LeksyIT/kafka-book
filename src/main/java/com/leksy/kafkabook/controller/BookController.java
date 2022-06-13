package com.leksy.kafkabook.controller;

import com.leksy.kafkabook.dto.RequestDTO;
import com.leksy.kafkabook.dto.request.BookDTO;
import com.leksy.kafkabook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class BookController {

    private final BookService bookService;
    private final RestTemplate restTemplate;

    @PostMapping("/get_from_kafka")
    public HttpEntity<BookDTO> getFromKafkaAndSend(@RequestBody RequestDTO requestDTO) {
        return bookService.createRequest(requestDTO);
    }
}

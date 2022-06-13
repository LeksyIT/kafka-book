package com.leksy.kafkabook.service;

import com.leksy.kafkabook.dto.RequestDTO;
import com.leksy.kafkabook.dto.request.BookDTO;
import org.springframework.http.HttpEntity;

public interface BookService {

    HttpEntity<BookDTO> createRequest(RequestDTO requestDTO);
}

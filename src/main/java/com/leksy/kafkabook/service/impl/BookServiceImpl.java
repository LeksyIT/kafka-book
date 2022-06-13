package com.leksy.kafkabook.service.impl;

import com.leksy.kafkabook.dto.RequestDTO;
import com.leksy.kafkabook.dto.request.BookDTO;
import com.leksy.kafkabook.exception.NotValidHttpMethodException;
import com.leksy.kafkabook.service.BookService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final RestTemplate restTemplate;

    private HttpEntity<BookDTO> deleteRequest(RequestDTO requestDTO){
        restTemplate.delete(requestDTO.getUrl() + "?id=" + requestDTO.getParameters().get("id"),BookDTO.class);
    return new HttpEntity<>(requestDTO.getBody());
    }

    @Override
    public HttpEntity<BookDTO> createRequest(RequestDTO requestDTO) {
        HttpEntity<BookDTO> entity = new HttpEntity<>(requestDTO.getBody());
        return switch (requestDTO.getHttp()) {
            case ("GET") -> restTemplate.getForEntity(requestDTO.getUrl() + "?id=" + requestDTO.getParameters().get("id"),BookDTO.class);
            case ("DELETE") -> deleteRequest(requestDTO);
            case ("PUT") -> restTemplate.exchange(requestDTO.getUrl() + "?id=" + requestDTO.getParameters().get("id"), HttpMethod.PUT, entity, BookDTO.class);
            case ("POST") -> restTemplate.postForEntity(requestDTO.getUrl(), entity, BookDTO.class);
            default -> throw new NotValidHttpMethodException();
        };
    }
}

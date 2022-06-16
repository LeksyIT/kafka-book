package com.leksy.kafkabook.service.impl;

import com.leksy.kafkabook.dto.RequestDTO;
import com.leksy.kafkabook.dto.request.BookDTO;
import com.leksy.kafkabook.exception.NotAllFieldsAreFilledInException;
import com.leksy.kafkabook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final RestTemplate restTemplate;

    private HttpEntity<BookDTO> deleteRequest(RequestDTO requestDTO) {
        restTemplate.delete(requestDTO.getUrl() + "?id=" + requestDTO.getParameters().get("id"), BookDTO.class);
        return new HttpEntity<>(requestDTO.getBody());
    }

    @KafkaListener(topics = "reflectoring-1", groupId = "json")
    public void listenFromKafka(RequestDTO requestDTO) {
        createRequest(requestDTO);
        System.out.println("Message: " + requestDTO);
    }

    @Override
    public HttpEntity<BookDTO> createRequest(RequestDTO requestDTO) {
        if (checkForValidField(requestDTO)) {
            throw new NotAllFieldsAreFilledInException("Нет необходимых полей в JSONe");
        }
        HttpEntity<BookDTO> entity = new HttpEntity<>(requestDTO.getBody());
        return switch (requestDTO.getHttp()) {
            case ("GET") -> restTemplate.getForEntity(requestDTO.getUrl() + "?id=" + requestDTO.getParameters().get("id"), BookDTO.class);
            case ("DELETE") -> deleteRequest(requestDTO);
            case ("PUT") -> restTemplate.exchange(requestDTO.getUrl() + "?id=" + requestDTO.getParameters().get("id"), HttpMethod.PUT, entity, BookDTO.class);
            case ("POST") -> restTemplate.postForEntity(requestDTO.getUrl(), entity, BookDTO.class);
            default -> null;
        };
    }

    private boolean checkForValidField(RequestDTO requestDTO) {
        //Check URL
        if (requestDTO.getUrl() == null || requestDTO.getUrl().equals("")) {
            return true;
        }
        //Check HttpMethod
        if (requestDTO.getHttp() == null || requestDTO.getHttp().equals("")) {
            return true;
        }
        //Check Header
        if (requestDTO.getHeader() == null) {
            return true;
        }
        //Check GET DELETE PUT
        if (checkParam(requestDTO))
            return requestDTO.getParameters() == null || requestDTO.getParameters().get("id") == null || requestDTO.getParameters().get("id").equals("");
        //Check POST PUT
        if (requestDTO.getHttp().equals("POST") || requestDTO.getHttp().equals("PUT")) {
            return requestDTO.getBody() == null ||
                    requestDTO.getBody().getAuthorName() == null || requestDTO.getBody().getAuthorName().equals("") ||
                    requestDTO.getBody().getPublication() == null || requestDTO.getBody().getPublication().equals("") ||
                    requestDTO.getBody().getPageCount() == null ||
                    requestDTO.getBody().getTitle() == null || requestDTO.getBody().getTitle().equals("") ||
                    requestDTO.getBody().getPublishedDate() == null;
        }
        return false;
    }

    private boolean checkParam(RequestDTO requestDTO) {
        return requestDTO.getHttp().equals("PUT") || requestDTO.getHttp().equals("DELETE") || requestDTO.getHttp().equals("GET");
    }
}

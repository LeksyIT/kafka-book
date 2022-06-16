package com.leksy.kafkabook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leksy.kafkabook.dto.request.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private String http;
    @JsonProperty("URL")
    private String url;
    private BookDTO body;
    private Map<String, String> header;
    private Map<String, String> parameters;
}

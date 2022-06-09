package com.leksy.kafkabook.dto;

import com.leksy.kafkabook.dto.request.BookPathVariablesDTO;
import com.leksy.kafkabook.dto.request.HeaderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private String http;
    private String url;
    private HeaderDTO headerDTO;
    private BookPathVariablesDTO bookPathVariablesDTO;
}

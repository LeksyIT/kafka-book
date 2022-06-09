package com.leksy.kafkabook.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookPathVariablesDTO {
    private String title;
    private String authorName;
}

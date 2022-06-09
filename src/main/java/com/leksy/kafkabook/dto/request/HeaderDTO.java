package com.leksy.kafkabook.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeaderDTO {
    private String accept;
    private String cacheControl;
}

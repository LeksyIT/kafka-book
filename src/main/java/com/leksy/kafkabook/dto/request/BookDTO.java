package com.leksy.kafkabook.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String authorName;
    private Integer pageCount;
    private Date publishedDate;
    private String publication;
}

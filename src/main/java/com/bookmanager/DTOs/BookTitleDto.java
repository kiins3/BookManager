package com.bookmanager.DTOs;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BookTitleDto {
    private String title;

    private String author;

    private String publisher;

    private LocalDate publicationDate;

    private String status;

    private int copies;
}

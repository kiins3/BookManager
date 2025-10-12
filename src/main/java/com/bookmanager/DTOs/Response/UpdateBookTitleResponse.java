package com.bookmanager.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UpdateBookTitleResponse {
    private String title;

    private String author;

    private String publisher;

    private LocalDate publicationDate;

    private Double compensationCost;

    private String status;
}

package com.bookmanager.DTOs.Response.Book;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GetAllBookResponse {
    private long id;

    private String title;

    private String publisher;

    private LocalDate publicationDate;

    private String status;

    private Double compensationCost;

    private String Author;
}

package com.bookmanager.DTOs.Response.Book;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GetBookByIdResponse {
    private long id;

    private String Title;

    private String Author;

    private String publisher;

    private LocalDate publicationDate;

    private Double compensationCost;

    private String Status;
}

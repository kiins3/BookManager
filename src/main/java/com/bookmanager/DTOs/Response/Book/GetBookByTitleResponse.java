package com.bookmanager.DTOs.Response.Book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GetBookByTitleResponse {
    private long id;

    private String Title;

    private String Author;

    private String publisher;

    private LocalDate publicationDate;

    private Double compensationCost;

    private String Status;
}

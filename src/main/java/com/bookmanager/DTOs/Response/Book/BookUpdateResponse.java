package com.bookmanager.DTOs.Response.Book;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookUpdateResponse {
    private String title;

    private String author;

    private String publisher;

    private String status;

    private LocalDate publishcationDate;

    private Double compensationCost;

}

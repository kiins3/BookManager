package com.bookmanager.DTOs.Response.Book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ReturnBookResponse {
    private Long userId;

    private String name;

    private Long bookId;

    private String bookTitle;

    private LocalDate borrowDate;

    private LocalDate returnDate;

    private Double compensationCost;

    private String note;

    private String status;
}

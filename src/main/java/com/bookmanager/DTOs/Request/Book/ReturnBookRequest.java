package com.bookmanager.DTOs.Request.Book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ReturnBookRequest {
    private Long cardId;

    private LocalDate returnDate;

    private String status;
}

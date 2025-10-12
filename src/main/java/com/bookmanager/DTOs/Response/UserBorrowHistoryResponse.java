package com.bookmanager.DTOs.Response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserBorrowHistoryResponse {
    private Long cardId;

    private Long bookId;

    private String bookTitle;

    private LocalDate borrowDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private String status;
}
